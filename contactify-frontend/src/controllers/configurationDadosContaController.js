import { UserService } from "../service/userService.js";
import { showDetailsFailure } from "../utils/detailsFailure.js";
import { showDetailsSuccess } from "../utils/detailsSuccess.js";

const buttonConfirmDadosConta = document.querySelector(".button-confirm-dados-conta");
const fade = document.querySelector(".fade");
const containerConfirm = document.querySelector(".confirmation-configuration-user");

let userId = undefined;
let box = undefined;

document.addEventListener('DOMContentLoaded', async () => {

	userId = localStorage.getItem("userId");
			
	const user = await UserService.findUserDetails(userId);

	const date = new Date(user.person.birthDate);
	const formatted = date.toLocaleDateString(); // formato do seu sistema local

	const [dia, mes, ano] = formatted.split("/");

	const dataFormatada = `${ano}-${mes}-${dia}`; // yyyy-MM-dd

	const objUser = {
		id: user.id,
		email: user.email,
		password: user.password,
		userStatus: user.userStatus,
		userAdmin: user.userAdmin,
		person: {
			firstName: user.person.firstName,
			lastName: user.person.lastName,
			birthDate: dataFormatada,
			email: user.person.email,
			phone: user.person.phone,
			address: user.person.address,
			complement: user.person.complement,
			number: user.person.number,
			nationality: user.person.nationality,
			gender: user.person.gender,
			cpf: user.person.cpf,
			rg: user.person.rg
		}
	}

	document.querySelector("#email").value = objUser.email;
	document.querySelector("#password").value = objUser.password;

	const inputs = document.querySelectorAll(".box-configuration-group-data-user input");

	let objectInputs = {
		inputEmail: {typeInput: undefined, valueInput: undefined},
		inputPassword: {typeInput: undefined, valueInput: undefined}
	};

	buttonConfirmDadosConta.style.display = "none";
	inputs.forEach(input => {
		input.addEventListener('input', () => {
			input.classList.add("active-input-alterado");

			if (input.id === "email") {
				objectInputs.inputEmail = { typeInput: "email", valueInput: input.value };
			}
			if (input.id === "password") {
				objectInputs.inputPassword = { typeInput: "password", valueInput: input.value };
			}

			buttonConfirmDadosConta.style.display = "initial";
		});
	});

	buttonConfirmDadosConta.addEventListener('click', () => {

	const container = document.querySelector(".confirmation-configuration-user-body");
		box = document.createElement("div");
		box.classList.add("confirmation-configuration-user-body-comparation");

		let openModal = true;

		if (objectInputs.inputEmail.valueInput === objUser.email) {
			document.querySelector("#email").classList.remove("active-input-alterado");
			objectInputs.inputEmail = {typeInput: undefined, valueInput: undefined};
		}
		if (objectInputs.inputPassword.valueInput === objUser.password) {
			document.querySelector("#password").classList.remove("active-input-alterado");
			objectInputs.inputPassword = {typeInput: undefined, valueInput: undefined};
		}

		if (openModal == true) {
			if (objectInputs.inputEmail.typeInput === "email") {
				box.innerHTML += `
					<div class="confirmation-configuration-user-body-comparation-container">
						<p class="p-info-comparation">Email:</p>
						<div class="flex-contianer">
							<div class="confirmation-configuration-user-body-comparation antes">
								<label for="cpf">Antes</label>
								<input type="text" id="email" value="${objUser.email}" disabled>
							</div>
							<div class="confirmation-configuration-user-body-comparation depois">
								<label for="cpf">Depois</label>
								<input type="text" id="email" value="${objectInputs.inputEmail.valueInput}" disabled>
							</div>
						</div>
					</div>
				`;
			}

			if (objectInputs.inputPassword.typeInput === "password") {
				box.innerHTML += `
					<div class="confirmation-configuration-user-body-comparation-container">
						<p class="p-info-comparation">Senha:</p>
						<div class="flex-contianer">
							<div class="confirmation-configuration-user-body-comparation antes">
								<label for="cpf">Antes</label>
								<input type="text" id="password" value="${objUser.password}" disabled>
							</div>
							<div class="confirmation-configuration-user-body-comparation depois">
								<label for="cpf">Depois</label>
								<input type="text" id="password" value="${objectInputs.inputPassword.valueInput}" disabled>
							</div>
						</div>
					</div>
				`;
			}
		}

		if (box.innerHTML == "") {
			buttonConfirmDadosConta.style.display = "none";
			return;
		}
		else {
			container.appendChild(box);
		
			fade.classList.add("active");
			containerConfirm.classList.add("active");
		
			document.querySelector(".confirmation-configuration-user-footer button").addEventListener('click', async () => {
		
				if (user.userStatus === "ACTIVE") {
					user.userStatus = 1;
				}
				else if (user.userStatus === "INACTIVE") {
					user.userStatus = 2;
				}
				else {
					user.userStatus = 3;
				}
			
				if (user.userAdmin === "ALLOWED") {
					user.userAdmin = 1;
				}
				else if (user.userAdmin === "NOT_ALLOWED") {
					user.userAdmin = 2;
				}
			
				const userUpdate = {
					id: user.id,
					email: document.querySelector("#email").value,
					password: document.querySelector("#password").value,
					userStatus: user.userStatus,
					userAdmin: user.userAdmin,
						person: {
							id: user.person.id,
							firstName: user.person.firstName,
							lastName: user.person.lastName,
							birthDate: user.person.birthDate,
							email: user.person.email,
							phone: user.person.phone,
							address: user.person.address,
							complement: user.person.complement,
							number: user.person.number,
							nationality: user.person.nationality,
							gender: user.person.gender,
							cpf: user.person.cpf,
							rg: user.person.rg
						}
				}
		
				const loading = document.querySelector(".container-loading");
		
				try {
					loading.style.display = "flex";
		
					const data = await UserService.update(userUpdate);
		
					if (data != null || data != undefined) {
						localStorage.setItem("updateSucess", "true");
						window.location.reload();
					}
		
					if (localStorage.getItem("updateSucess") === "true") {

						fade.classList.remove("active");
						containerConfirm.classList.remove("active");
						buttonConfirmDadosConta.style.display = "none";
		
						inputs.forEach(input => {
							input.classList.remove("active-input-alterado");
						});
		
						localStorage.removeItem("updateSucess");
					}
				}
				catch (error) {
					showDetailsFailure(error.message);
				}
				finally {
					loading.style.display = "none";
				}
			});
		}
	});
});

document.querySelector(".button-exit-modal-confirmation-configuration-dados-pessoais").addEventListener('click', () => {
	const fade = document.querySelector(".fade");
	const containerConfirm = document.querySelector(".confirmation-configuration-user");
	fade.classList.remove("active");
	containerConfirm.classList.remove("active");
});