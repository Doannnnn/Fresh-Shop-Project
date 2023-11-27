const bodyCartDetail = $("#tbCartDetail");
const username = $(`#username`).text();
const quantityCartDetail = $('#quantityCartDetail');
const fetchAllCartDetail = async () => {
    try {
        return await $.ajax({
            url: `http://localhost:8080/api/cart-detail/${username}`,
            method: "GET",
            dataType: "json"
        });
    } catch (error) {
        console.error("Error:", error);
    }
};
const renderCartDetail = (obj, isLast, cartDetails) => {
    return `
            <li>
                <a href="#" class="photo"><img src="${obj.product.images[0].url}" class="cart-thumb" alt=""/></a>
                <h6><a href="#">${obj.product.name}</a></h6>
                <p>${obj.quantity + "x"} - <span class="price">${"$ " + obj.product.price}</span></p>
                    ${isLast ? '<li class="total">\n' +
        '<a href="/cart" class="btn btn-default hvr-hover btn-cart">VIEW CART</a>\n' +
        `<span class="float-right"><strong>Total</strong>: $ ${calculateTotal(cartDetails)}</span>\n` +
        '</li>' : ''}
            </li>
            `;
};

const calculateTotal = (cartDetails) => {
    let total = 0;
    cartDetails.forEach(item => {
        total += item.quantity * item.product.price;
    });
    return total.toFixed(3);
};

const quantity = (cartDetails) => {
    const totalQuantity = cartDetails.length;
    quantityCartDetail.text(totalQuantity.toString());
};
const getAllCartDetail = async () => {
    const cartDetails = await fetchAllCartDetail();
    console.log(cartDetails);

    cartDetails.forEach((item, index) => {
        const isLast = index === cartDetails.length - 1;
        const str = renderCartDetail(item, isLast, cartDetails);
        bodyCartDetail.append(str);
    });

    quantity(cartDetails);
};

getAllCartDetail();

function addCartDetail(productId) {
    if(!checkUserIsExist()){
        webToast.Danger({
            status: 'Please login to add product to cart!',
            message: '',
            delay: 3000,
            align: 'topcenter'
        });
        return;
    }
    const quantity = 1;
    const username = $('#username').text();

    const cardDetail = {
        quantity,
        productId,
        username
    };

    try {
        $.ajax({
            url: `http://localhost:8080/api/cart-detail`,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(cardDetail)
        })
            .done((response) => {
                webToast.Success({
                    status: 'Thêm Vào giỏ hàng thành công',
                    message: '',
                    delay: 2000,
                    align: 'topright'
                });

                $('#tbCartDetail').empty();
                getAllCartDetail();
            })
            .fail((error) => {
                webToast.Danger({
                    status: 'Thêm Vào giỏ hàng thất bại',
                    message: '',
                    delay: 2000,
                    align: 'topright'
                });
            });
    } catch (error) {
        console.error('Error:', error);
    }
};

$('#addCartDetailQuantity').on('click', function () {
    const productId = $('#productId').text();
    const quantity = $('#quantity').val();
    const username = $('#username').text();

    const cardDetail = {
        quantity,
        productId,
        username
    };

    try {
        $.ajax({
            url: `http://localhost:8080/api/cart-detail`,
            method: 'PATCH',
            contentType: 'application/json',
            data: JSON.stringify(cardDetail)
        })
            .done((response) => {
                webToast.Success({
                    status: 'Thêm Vào giỏ hàng thành công',
                    message: '',
                    delay: 2000,
                    align: 'topright'
                });

                $('#modelDetail').modal('hide');
                $('#tbCartDetail').empty();
                getAllCartDetail();
            })
            .fail((error) => {
                webToast.Danger({
                    status: 'Thêm Vào giỏ hàng thất bại',
                    message: '',
                    delay: 2000,
                    align: 'topright'
                });

                const errorObject = error.responseJSON;

                if (errorObject) {
                    Object.keys(errorObject).forEach(key => {
                        const errorMessage = errorObject[key];
                        $(`#quantityError`).text(errorMessage);
                        $(`#quantity`).addClass('error');
                    });
                }
            });
    } catch (error) {
        console.error('Error:', error);
    }
});

function deleteCartDetail(cartDetailId){
    if (confirm("Bạn có chắc chắn muốn xóa?")) {
        try {
            $.ajax({
                url: `http://localhost:8080/api/cart-detail/${cartDetailId}`,
                method: 'DELETE',
            })
                .done((response) => {
                    webToast.Success({
                        status: 'Xóa thành công',
                        message: '',
                        delay: 2000,
                        align: 'topright'
                    });

                    const deleteRow = $('#tr_' + cartDetailId);
                    deleteRow.remove();

                    $('#tbCartDetail').empty();
                    getAllCartDetail();
                })
                .fail((error) => {
                    webToast.Danger({
                        status: 'Xóa thất bại',
                        message: '',
                        delay: 2000,
                        align: 'topright'
                    });
                });
        } catch (error) {
            console.error('Error:', error);
        }
    }
}

$("#placeOrder").on("click", function () {
    const totalString = $('#total').text();
    const total = parseFloat(totalString.replace('$', ''));
    const username = $('#username').text();
    const shippingMethod = $('input[name="shipping-option"]:checked').closest('.custom-control').find('.custom-control-label').text();

    const bill = {
        total,
        username,
        shippingMethod
    };

    try {
        $.ajax({
            url: `http://localhost:8080/api/bill`,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(bill)
        })
            .done((response) => {
                webToast.Success({
                    status: 'Order thành công',
                    message: '',
                    delay: 2000,
                    align: 'topright'
                });

                $("#tbCartDetails").empty();
                $('#tbCartDetail').empty();
                getAllCartDetail();

                $('#modelOrder').modal('hide');
            })
            .fail((error) => {
                webToast.Danger({
                    status: 'Order thất bại',
                    message: '',
                    delay: 2000,
                    align: 'topright'
                });
            });
    } catch (error) {
        console.error('Error:', error);
    }
});





