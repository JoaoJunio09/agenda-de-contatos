const container = document.querySelector(".message-confirmation");

export function openMessageConfirmation() {
	container.classList.add("show-message-confirmation");

	const buttonCancel = document.querySelector(".buttonCancel");
	const buttonConfirmation = document.querySelector(".buttonConfirmation");
	const buttonClose = document.querySelector(".img-button-cancel");

	if (buttonClose) {
		buttonClose.addEventListener('click', () => {
			container.classList.remove("show-message-confirmation");
			return false;
		});
	}

	if (buttonCancel) {
		buttonCancel.addEventListener('click', () => {
			container.classList.remove("show-message-confirmation");
			return false;
		});
	}
	
	if (buttonConfirmation) {
		buttonConfirmation.addEventListener('click', () => {
			container.classList.remove("show-message-confirmation");
			return true;
		});
	}
}