const buttonOpenModal = document.querySelector(".button-nova-conta");
const buttonExitModal = document.querySelector(".button-exit-modal");
const divModal = document.querySelector(".modal-nova-conta");
const fade = document.querySelector(".fade");

buttonOpenModal.addEventListener('click', function() {
	divModal.classList.add("show");
	fade.classList.add("show");
});

buttonExitModal.addEventListener('click', function() {
	divModal.classList.remove("show");
	fade.classList.remove("show");
});