import { showDetailsFailure } from "../utils/detailsFailure.js";

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

const btnNextPage = document.getElementById("btn-next-page");
const btnPreviousPage = document.querySelector("#btn-previous-page");
const slider = document.querySelector(".slider");

let currentStage = 0;

showDetailsFailure();
console.log("Page 1");
btnNextPage.addEventListener('click', () => {
    if (currentStage === 0) {
        currentStage++;
        updateSlider();
    }
    else if (currentStage === 1) {
        currentStage++;
        updateSlider();
        console.log("Page 2");
    }
    else if (currentStage === 2) {
        console.log("Page 3");
    }
    console.log(currentStage);
});

btnPreviousPage.addEventListener('click', () => {
    if (currentStage > 0) {
        currentStage--;
        updateSlider();
    }
});

function updateSlider() {
    let percentage = currentStage * -100;
    slider.style.transform = `translateX(${percentage}%)`;
    console.log(percentage);
}