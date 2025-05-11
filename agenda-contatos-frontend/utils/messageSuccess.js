

const messageSuccess = document.querySelector(".message-success");
const buttonClose = document.querySelector("#button-close-message-success");

async function openMessageSuccess(message) {
	messageSuccess.querySelector(".message-success p").innerHTML = message;

	setTimeout(() => {
		messageSuccess.classList.add("show");

		setInterval(() => {
			messageSuccess.classList.remove("show");
		}, 12000);
	}, 1500);
	
}

function closeMessageSuccess() {
	messageSuccess.classList.remove("show");
}

buttonClose.addEventListener('click', closeMessageSuccess);

export { openMessageSuccess, closeMessageSuccess};