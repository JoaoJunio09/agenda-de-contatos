"use strict";
const fade = document.querySelector("#fade");
const btnsOpenModalEditUser = document.querySelectorAll(".btn-open-modal-edit-user");
const btnsCloseModalEditUser = document.querySelectorAll(".btn-close-modal-edit-user");
const modalEdit = document.querySelector(".modal-edit-user");
btnsOpenModalEditUser.forEach(btn => {
    btn.addEventListener('click', () => {
        modalEdit.classList.add("show-modal-edit-user");
        fade.classList.add("show-fade");
    });
});
btnsCloseModalEditUser.forEach(btn => {
    btn.addEventListener('click', () => {
        modalEdit.classList.remove("show-modal-edit-user");
        fade.classList.remove("show-fade");
    });
});
