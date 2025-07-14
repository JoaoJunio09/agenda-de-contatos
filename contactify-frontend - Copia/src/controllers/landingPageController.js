document.addEventListener('DOMContentLoaded', () => {
	const observerElementsAnimation = new IntersectionObserver(entries => {
		entries.forEach(entry => {
			if (entry.isIntersecting) {
				entry.target.classList.add("ativo");
				entry.target.classList.add("active");
			}
		});
	}, {
		threshold: 0.2
	});

	document.querySelectorAll(".box-funcionalidades").forEach(element => {
		observerElementsAnimation.observe(element);
	});

	const img = document.querySelector(".box-video-and-img img");
	observerElementsAnimation.observe(img);

	const boxText = document.querySelector(".box-text");
	observerElementsAnimation.observe(boxText);

	const boxAdquirirAgenda = document.querySelector(".box-adquirir-agenda");
	const boxContent = document.querySelector(".box-content");

	observerElementsAnimation.observe(boxAdquirirAgenda);
	observerElementsAnimation.observe(boxContent);
});

const buttonsRegister = document.querySelectorAll(".box-content");

buttonsRegister.forEach(element => {
	element.addEventListener('click', () => {
		window.location.href = "../../src/pages/cadastroUsuarios.html";
	});
})