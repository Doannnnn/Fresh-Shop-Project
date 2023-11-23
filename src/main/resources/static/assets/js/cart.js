$(document).ready(function() {
    const bodyCartDetail = $("#tbCartDetail");
    const username = $('#username').text();

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

    const renderCartDetail = (obj) => {
        return `
            <li>
                <a href="#" class="photo"><img src="${obj.product.images[0].url}" class="cart-thumb" alt=""/></a>
                <h6><a href="#">${obj.product.name}</a></h6>
                <p>${obj.quantity + "x"} - <span class="price">${"$ " + obj.product.price}</span></p>
            </li>
            `;
    };

    const getAllCartDetail = async () => {
        const cartDetails = await fetchAllCartDetail();
        console.log(cartDetails);

        cartDetails.forEach(item => {
            const str = renderCartDetail(item);
            bodyCartDetail.append(str);
        });
    };

    getAllCartDetail();
});