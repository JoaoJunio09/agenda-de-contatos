const messageSuccess = document.querySelector(".message-success");
const buttonClose = document.querySelector("#button-close-message-success");

function openMessageSuccess(message) {
	messageSuccess.querySelector(".message-success p").innerHTML = message;
	messageSuccess.classList.add("show");

	setInterval(() => {
		messageSuccess.classList.remove("show");
	}, 4000);
}

function closeMessageSuccess() {
	messageSuccess.classList.remove("show");
}

buttonClose.addEventListener('click', openMessageSuccess);

export { openMessageSuccess, closeMessageSuccess};