
const main = document.querySelector("main");
const bullets = document.querySelectorAll(".bullets span");
const images = document.querySelectorAll(".image");

// inputs.forEach((inp) => {
//     inp.addEventListener("focus", () => {
//         inp.classList.add("active");
//     });
//     inp.addEventListener("blur", () => {
//         if (inp.value != "") return;
//         inp.classList.remove("active");
//     });
// });
//
//

function moveSlider() {
    let index = this.dataset.value;

    let currentImage = document.querySelector(`.img-${index}`);
    images.forEach((img) => img.classList.remove("show"));
    currentImage.classList.add("show");

    const textSlider = document.querySelector(".text-group");
    textSlider.style.transform = `translateY(${-(index - 1) * 2.2}rem)`;

    bullets.forEach((bull) => bull.classList.remove("active"));
    this.classList.add("active");
}

bullets.forEach((bullet) => {
    bullet.addEventListener("click", moveSlider);
});


const formRegister = $('#register');
formRegister.validate({
    onkeyup: function (element) {
        $(element).valid()
    },
    onclick: false,
    onfocusout: false,
    rules: {
        fullName: {
            required: true,
            minlength: 5,
            maxlength: 25
        },
        phoneNumber: {
            required: true,
            isPhone: true
        },
        email: {
            required: true,
            isEmail: true
        },
        address: {
            required:true,
        },
        username: {
            required: true,
            minlength: 5
        },
        password: {
            required: true,
            minlength: 6
        },
        // passwordCm: {
        //     required: true,
        // }
    },
    messages: {
        fullName: {
            required: 'Vui lòng nhập fullName đầy đủ',
            minlength: 'FullName tối thiểu là 5 ký tự',
            maxlength: 'FullName tối đa là 25 ký tự'
        },
        phoneNumber: {
            required: 'Vui lòng nhập phone đầy đủ'
        },
        email: {
            required: 'Vui lòng nhập email đầy đủ'
        },
        address: {
            required: 'Vui lòng nhập địa chỉ đầy đủ'
        },
        username: {
            required: 'Vui lòng nhập username đầy đủ',
            minlength: 'Username tối thiểu là 5 ký tự',
        },
        password: {
            required: 'Vui lòng nhập password đầy đủ',
            minlength: 'Password tối thiểu là 6 ký tự',
        },

    },

})
$.validator.addMethod("isEmail", function (value, element) {
    return this.optional(element) || /^[a-z]+@[a-z]+\.[a-z]+$/i.test(value);
}, "Vui lòng nhập đúng định dạng email");
$.validator.addMethod("isPhone", function (value, element) {
    return this.optional(element) || /^[0][0-9]{9}$/i.test(value);
}, "Vui lòng nhập 10 số bắt đầu là 0");
$.validator.addMethod("isNumber", function (value, element) {
    return this.optional(element) || /^[0-9]*$/i.test(value);
}, "Vui lòng nhập tiền giao dịch bằng ký tự số");