const bodyProduct = $("#tbProduct");
const showModalCreate = $("#showModalCreate");
const createButton = $('#createButton');
const updateButton = $('#updateButton');
let existingFiles = [];
const cloudName = 'dbci5tvbu';
const unsignedUploadPrefix = 'zympvkzi';
const url = `https://api.cloudinary.com/v1_1/${cloudName}/upload`;

$('#createModal, #updateModal').on('hidden.bs.modal', function (e) {
    const modal = $(this);

    $('#image-preview').empty();
    existingFiles = [];

    const form = modal.find('form')[0];
    form.reset();
    modal.find('input[type="text"], input[type="email"], input[type="file"], input[type="number"], input[type="date"]').removeClass('error');
    modal.find('.error-message').text('');
});

const fetchAllProduct = async () => {
    try {
        return await $.ajax({
            url: "http://localhost:8080/api/product",
            method: "GET",
            dataType: "json"
        });
    } catch (error) {
        console.error("Error:", error);
    }
};

const renderProduct = (obj) => {
    return `
            <tr id="tr_${obj.id}">
                <td>${obj.id}</td>
                <td><img src="${obj.images[0].url}" alt="" style="width: 100px; height: 70px"></td>
                <td>${obj.name}</td>
                <td>${obj.description}</td>
                <td>${obj.price}</td>
                <td>${obj.category}</td>
                <td>${obj.expirationDate}</td>
                <td>
                      <button class="btn btn-secondary edit" onclick="showModalUpdate(${obj.id})">
                            <i class="far fa-edit"></i>
                      </button>
                     <button class="btn btn-danger delete" onclick="deleteProduct(${obj.id})">
                          <i class="fa-solid fa-trash"></i>
                      </button>
                </td>
            </tr>
        `;
};

const getAllProduct = async () => {
    const products = await fetchAllProduct();
    console.log(products);

    products.forEach(item => {
        const str = renderProduct(item);
        bodyProduct.append(str);
    });
};

getAllProduct();

showModalCreate.on("click", function () {
    $("#createModal").modal("show");
});

createButton.on('click', async function () {
    const name = $('#nameCreate').val();
    const price = $('#priceCreate').val();
    const description = $('#descriptionCreate').val();
    const category = $('#categoryCreate').val();
    const expirationDate = $('#expirationDateCreate').val();
    const fileInput = $('#imagesCreate');

    const files = fileInput[0].files;

    $(`#loading`).css('display', 'block');

    const url = await handleUpload(files);
    const images = url.map(url => ({ url }));

    const product = {
        name,
        price,
        description,
        category,
        expirationDate,
        images
    };

    try {
        const response = await $.ajax({
            url: 'http://localhost:8080/api/product',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(product)
        })

            .done((response) => {
                Swal.fire({
                    icon: "success",
                    title: "Thêm mới thành công",
                    showConfirmButton: false,
                    timer: 1500
                });
                const productData = response, str = renderProduct(productData);
                bodyProduct.append(str);

                $('#createModal').modal('hide');
            })
            .fail((error) => {
                const errorObject = error.responseJSON;

                if (errorObject) {
                    Object.keys(errorObject).forEach(key => {
                        const errorMessage = errorObject[key];
                        const fieldName = key.split('.').pop();
                        $(`#${fieldName}CreateError`).text(errorMessage);
                        $(`#${fieldName}Create`).addClass('error');
                    });
                }
            });
    } catch (error) {
        console.error('Lỗi:', error);
    } finally {
        $('#loading').css('display', 'none');
    }
});

const handleUpload = async (files) => {
    if (!files || files.length === 0) {
        return [];
    }

    const uploadUrls = [];

    for (const file of files) {
        const formData = new FormData();
        formData.append("file", file);
        formData.append("upload_preset", unsignedUploadPrefix);

        try {
            const response = await $.ajax({
                url: url,
                method: 'POST',
                data: formData,
                processData: false,
                contentType: false
            });

            console.log('Tải lên thành công', response.url);
            uploadUrls.push(response.url);
        } catch (error) {
            console.error('Tải lên thất bại', error);
        }
    }

    return uploadUrls;
};

$(document).ready(function () {
    const preview = $("#image-preview");

    function addFilesToInput(input, newFiles) {
        const validNewFiles = Array.from(newFiles).filter(function (file) {
            return file instanceof File;
        });

        const newFileList = new DataTransfer();
        existingFiles.forEach(function (file) {
            newFileList.items.add(file);
        });

        validNewFiles.forEach(function (file) {
            if (!existingFiles.some(function (existingFile) {
                return existingFile.name === file.name;
            })) {
                existingFiles.push(file);
                newFileList.items.add(file);
            }
        });

        input.files = newFileList.files;
    }

    function previewImages() {
        const input = $("#imagesCreate")[0];
        const files = input.files;

        addFilesToInput(input, files);

        preview.empty();

        for (let i = 0; i < existingFiles.length; i++) {
            const file = existingFiles[i];
            const fileName = file.name;

            const reader = new FileReader();

            reader.onload = createImagePreview(file, fileName);

            reader.readAsDataURL(file);
        }
    }

    function createImagePreview(file, fileName) {
        return function (event) {
            const img = $("<img>").attr("src", event.target.result).css({
                width: "200px",
                height: "150px",
                objectFit: "cover",
                marginRight: "10px"
            });

            const deleteIcon = $("<span>")
                .addClass("delete-icon")
                .html("&times;")
                .css({
                    position: "relative",
                    bottom: "67px",
                    right: "8px"
                });

            deleteIcon.click(function () {
                img.remove();
                deleteIcon.remove();
                const input = $("#imagesCreate")[0];
                const index = existingFiles.indexOf(file);
                if (index !== -1) {
                    existingFiles.splice(index, 1);
                    const newFileList = new DataTransfer();
                    for (let j = 0; j < existingFiles.length; j++) {
                        newFileList.items.add(existingFiles[j]);
                    }
                    input.files = newFileList.files;
                }
            });

            preview.append(img).append(deleteIcon);
        };
    }

    $(`#imagesCreate`).change(previewImages);
});

function showModalUpdate(productId) {
    $.ajax({
        url: `http://localhost:8080/api/product/${productId}`,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            $('#productId').val(data.id);
            $('#nameUpdate').val(data.name);
            $('#priceUpdate').val(data.price);
            $('#descriptionUpdate').val(data.description);
            $('#expirationDateUpdate').val(data.expirationDate);
            $('#categoryUpdate').val(data.category);

            const imagePreview = $('#image-previewUpdate');
            imagePreview.empty();

            // Lặp qua danh sách ảnh và thêm chúng vào phần tử image-preview
            data.images.forEach(function (image) {
                const img = $("<img>").attr("src", image.url).css({
                    width: "200px",
                    height: "150px",
                    objectFit: "cover",
                    marginRight: "10px"
                });

                const deleteIcon = $("<span>")
                    .addClass("delete-icon")
                    .html("&times;")
                    .css({
                        position: "relative",
                        bottom: "67px",
                        right: "8px"
                    });

                deleteIcon.click(function () {
                    img.remove();
                    deleteIcon.remove();
                    const index = data.images.indexOf(image);
                    if (index !== -1) {
                        data.images.splice(index, 1);
                    }
                });

                imagePreview.append(img).append(deleteIcon);
            });

            $('#updateModal').modal('show');
        },
        error: function (error) {
            console.error('Lỗi:', error);
        }
    });
}


updateButton.on('click', async function () {
    const productId = $('#productId').val();
    const name = $('#nameUpdate').val();
    const price = $('#priceUpdate').val();
    const description = $('#descriptionUpdate').val();
    const category = $('#categoryUpdate').val();
    const expirationDate = $('#expirationDateUpdate').val();
    const fileInput = $('#imagesUpdate');

    const files = fileInput[0].files;

    $(`#loading`).css('display', 'block');

    const url = await handleUpload(files);
    const images = url.map(url => ({ url }));

    const product = {
        name,
        price,
        description,
        category,
        expirationDate,
        images
    };

        try {
            const response = await $.ajax({
                url: `http://localhost:8080/api/product/${productId}`,
                method: 'PATCH',
                contentType: 'application/json',
                data: JSON.stringify(product)
            })

                .done((response) => {
                    const updatedProduct = response;
                    const updateRow = $('#tr_' + productId);
                    const str = renderProduct(updatedProduct);
                    updateRow.replaceWith(str);
                    $('#updateModal').modal('hide');

                    Swal.fire({
                        icon: "success",
                        title: "Cập nhập thành công",
                        showConfirmButton: false,
                        timer: 1500
                    });
                })
                .fail((error) => {
                    const errorObject = error.responseJSON;

                    if (errorObject) {
                        Object.keys(errorObject).forEach(key => {
                            const errorMessage = errorObject[key];
                            const fieldName = key.split('.').pop();
                            $(`#${fieldName}UpdateError`).text(errorMessage);
                            $(`#${fieldName}Update`).addClass('error');
                        });
                    }
                });
        } catch (error) {
            console.error('Error:', error);
        } finally {
            $('#loading').css('display', 'none');
        }
});

function deleteProduct(productId) {
    if (confirm("Bạn có chắc chắn muốn xóa sản phẩm này?")) {
        $(`#loading`).css('display', 'block');

        setTimeout(async function () {
            try {
                $.ajax({
                    url: `http://localhost:8080/api/product/${productId}`,
                    type: 'DELETE',
                    success: function (data) {

                        const deleteRow = $('#tr_' + productId);
                        deleteRow.remove();

                        Swal.fire({
                            icon: "success",
                            title: "Xóa thành công",
                            showConfirmButton: false,
                            timer: 1500
                        });
                    },
                    error: function (error) {
                        console.error('Lỗi:', error);
                    }
                });
            } catch (error) {
                console.error('Error:', error);
            } finally {
                $('#loading').css('display', 'none');
            }
        }, 1500);
    }
}














