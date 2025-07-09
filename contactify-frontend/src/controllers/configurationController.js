import { UserService } from "../service/userService.js";

let userId = undefined;

const buttonExitAccount = document.querySelector("#button-exit-account");

buttonExitAccount.addEventListener('click', () => {
	localStorage.removeItem("userId");
	window.location.href = "../../index.html";
});

document.addEventListener('DOMContentLoaded', async () => {

	userId = localStorage.getItem("userId");
	
	const userLoginData = await UserService.findUserDetails(userId);
	
	document.querySelector(".info-account-data h2").textContent = userLoginData.person.firstName;
	document.querySelector(".info-account-data p").textContent = userLoginData.email;

	const menu_item_gerenciar_usuarios = document.querySelector(".menu-item-gerenciar-usuarios");

	if (localStorage.getItem('userAdmin') === "true") {
		menu_item_gerenciar_usuarios.style.display = "inherit";
	}
	else {
		menu_item_gerenciar_usuarios.style.display = "none";
	}
})