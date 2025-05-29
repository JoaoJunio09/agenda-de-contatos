const fade = document.querySelector("#fade") as HTMLDivElement;
const btnsOpenModalEditUser = document.querySelectorAll(".btn-open-modal-edit-user") ;
const btnsCloseModalEditUser = document.querySelectorAll(".btn-close-modal-edit-user");
const modalEdit = document.querySelector(".modal-edit-user") as HTMLDivElement;

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