const $button = document.querySelector('.cart');

$button.addEventListener('click', () => {
    // Kiểm tra trạng thái đăng nhập của người dùng
    if (isLoggedIn()) {
        // Người dùng đã đăng nhập
        // Thực hiện hành động bình thường
    } else {
        // Người dùng chưa đăng nhập
        // Chuyển hướng người dùng đến trang login
        window.location.href = '/login';
    }
});