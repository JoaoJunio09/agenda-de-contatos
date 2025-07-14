export function showDetailsSuccess(message) {
	const failure = document.querySelector(".container-details-success");
	const btnClose = document.querySelector("#btnCloseSuccess");
	const text = document.querySelector(".container-details-success p");
	const animation = document.querySelector(".animation-success");

	text.innerHTML = message;

	failure.classList.add("show-details-success");
	animation.classList.remove("show");
	void animation.offsetWidth;
	animation.classList.add("show");

	btnClose.addEventListener('click', () => {
		failure.classList.remove("show-details-success");
	});
	
	animation.removeEventListener('animationend', handleAnimationEnd);

	function handleAnimationEnd() {
		failure.classList.remove("show-details-success");
		animation.classList.remove("show");
	}

	animation.addEventListener('animationend', handleAnimationEnd);
}