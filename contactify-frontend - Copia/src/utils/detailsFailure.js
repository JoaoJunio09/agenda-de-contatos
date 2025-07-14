export function showDetailsFailure(message) {
	const failure = document.querySelector(".container-details-failure");
	const btnClose = document.querySelector("#btnCloseFailure");
	const text = document.querySelector(".container-details-failure p");
	const animation = document.querySelector(".animation");

	text.innerHTML = message;

	failure.classList.add("show-details-failure");
	animation.classList.remove("show");
	void animation.offsetWidth;
	animation.classList.add("show");

	btnClose.addEventListener('click', () => {
		failure.classList.remove("show-details-failure");
	});
	
	animation.removeEventListener('animationend', handleAnimationEnd);

	function handleAnimationEnd() {
		failure.classList.remove("show-details-failure");
		animation.classList.remove("show");
	}

	animation.addEventListener('animationend', handleAnimationEnd);
}

export function closeShowDetailsFailure() {
	const failure = document.querySelector(".container-details-failure");
	failure.classList.remove("show-details-failure");
}

export function showPasswordInvalid(message) {
	const container = document.querySelector(".container-password-invalid");
	const text = document.querySelector(".container-password-invalid p");
	const btnClose = document.querySelector(".btn-close-password-invalid");

	text.innerHTML = message;

	container.classList.add("show-password-invalid");

	btnClose.addEventListener('click', () => {
		container.classList.remove("show-password-invalid");
	})

	setTimeout(() => {
		container.classList.remove("show-password-invalid");
	}, 8000);
}