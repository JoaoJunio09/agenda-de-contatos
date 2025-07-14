import { UserService } from "../service/userService.js";
import { showDetailsFailure } from "../utils/detailsFailure.js";
import { showDetailsSuccess } from "../utils/detailsSuccess.js";

let userId = undefined;
let box = undefined;

const buttonConfirmDadosPessoais = document.querySelector(".button-confirm-dados-pessoais");
const fade = document.querySelector(".fade");
const containerConfirm = document.querySelector(".confirmation-configuration-user");

document.addEventListener('DOMContentLoaded', async () => {

	userId = localStorage.getItem("userId");
		
	const user = await UserService.findUserDetails(userId);

	const date = new Date(user.person.birthDate);
	const formatted = date.toLocaleDateString(); // formato do seu sistema local

	const [dia, mes, ano] = formatted.split("/");

	const dataFormatada = `${ano}-${mes}-${dia}`; // yyyy-MM-dd

	const person = {
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
	};

	document.querySelector("#firstName").value = person.firstName;
	document.querySelector("#lastName").value = person.lastName;
	document.querySelector("#birthDate").value = person.birthDate;
	document.querySelector("#email").value = person.email;
	document.querySelector("#phone").value = person.phone;
	document.querySelector("#address").value = person.address;
	document.querySelector("#complement").value = person.complement;
	document.querySelector("#number").value = person.number;
	document.querySelector("#nationality").value = person.nationality;
	document.querySelector("#gender").value = person.gender;
	document.querySelector("#cpf").value = person.cpf;
	document.querySelector("#rg").value = person.rg;

	const inputs = document.querySelectorAll(".box-configuration-group-data-user input");

	let objectInputs = {
		inputFirstName: {typeInput: undefined, valueInput: undefined},
		inputLastName: {typeInput: undefined, valueInput: undefined},
		inputBirthDate: {typeInput: undefined, valueInput: undefined},
		inputEmail: {typeInput: undefined, valueInput: undefined},
		inputPhone: {typeInput: undefined, valueInput: undefined},
		inputAddress: {typeInput: undefined, valueInput: undefined},
		inputComplement: {typeInput: undefined, valueInput: undefined},
		inputNumber: {typeInput: undefined, valueInput: undefined},
		inputNationality: {typeInput: undefined, valueInput: undefined},
		inputCpf: {typeInput: undefined, valueInput: undefined},
		inputRg: {typeInput: undefined, valueInput: undefined}
	};

	buttonConfirmDadosPessoais.style.display = "none";
	inputs.forEach(input => {
		input.addEventListener('input', () => {
			input.classList.add("active-input-alterado");

			if (input.id === "firstName") {
				objectInputs.inputFirstName = { typeInput: "firstName", valueInput: input.value} ;
			}
			if (input.id === "lastName") {
				objectInputs.inputLastName = { typeInput: "lastName", valueInput: input.value };
			}
			if (input.id === "birthDate") {
				objectInputs.inputBirthDate = { typeInput: "birthDate", valueInput: input.value };
			}
			if (input.id === "email") {
				objectInputs.inputEmail = { typeInput: "email", valueInput: input.value };
			}
			if (input.id === "phone") {
				objectInputs.inputPhone = { typeInput: "phone", valueInput: input.value };
			}
			if (input.id === "address") {
				objectInputs.inputAddress = { typeInput: "address", valueInput: input.value };
			}
			if (input.id === "complement") {
				objectInputs.inputComplement = { typeInput: "complement", valueInput: input.value };
			}
			if (input.id === "number") {
				objectInputs.inputNumber = { typeInput: "number", valueInput: input.value };
			}
			if (input.id === "nationality") {
				objectInputs.inputNationality = { typeInput: "nationality", valueInput: input.value };
			}
			if (input.id === "cpf") {
				objectInputs.inputCpf = { typeInput: "cpf", valueInput: input.value };
			}
			if (input.id === "rg") {
				objectInputs.inputRg = { typeInput: "rg", valueInput: input.value };
			}

			buttonConfirmDadosPessoais.style.display = "initial";
		});
	});

	buttonConfirmDadosPessoais.addEventListener('click', async () => {
		const container = document.querySelector(".confirmation-configuration-user-body");
		box = document.createElement("div");
		box.classList.add("confirmation-configuration-user-body-comparation");

		let openModal = true;

		if (objectInputs.inputFirstName.valueInput === person.firstName) {
			document.querySelector("#firstName").classList.remove("active-input-alterado");
			objectInputs.inputFirstName = {typeInput: undefined, valueInput: undefined};
		}
		if (objectInputs.inputLastName.valueInput === person.lastName) {
			document.querySelector("#lastName").classList.remove("active-input-alterado");
			objectInputs.inputLastName = {typeInput: undefined, valueInput: undefined};
		}
		if (objectInputs.inputBirthDate.valueInput === person.birthDate) {
			document.querySelector("#birthDate").classList.remove("active-input-alterado");
			objectInputs.inputBirthDate = {typeInput: undefined, valueInput: undefined};
		}
		if (objectInputs.inputEmail.valueInput === person.email) {
			document.querySelector("#email").classList.remove("active-input-alterado");
			objectInputs.inputEmail = {typeInput: undefined, valueInput: undefined};
		}
		if (objectInputs.inputPhone.valueInput === person.phone) {
			document.querySelector("#phone").classList.remove("active-input-alterado");
			objectInputs.inputPhone = {typeInput: undefined, valueInput: undefined};
		}
		if (objectInputs.inputAddress.valueInput === person.address) {
			document.querySelector("#address").classList.remove("active-input-alterado");
			objectInputs.inputAddress = {typeInput: undefined, valueInput: undefined};
		}
		if (objectInputs.inputComplement.valueInput === person.complement) {
			document.querySelector("#complement").classList.remove("active-input-alterado");
			objectInputs.inputComplement = {typeInput: undefined, valueInput: undefined};
		}
		if (objectInputs.inputNumber.valueInput === person.number) {
			document.querySelector("#number").classList.remove("active-input-alterado");
			objectInputs.inputNumber = {typeInput: undefined, valueInput: undefined};
		}
		if (objectInputs.inputNationality.valueInput === person.nationality) {
			document.querySelector("#nationality").classList.remove("active-input-alterado");
			objectInputs.inputNationality = {typeInput: undefined, valueInput: undefined};
		}
		if (objectInputs.inputCpf.valueInput === person.cpf) {
			document.querySelector("#cpf").classList.remove("active-input-alterado");
			objectInputs.inputCpf = {typeInput: undefined, valueInput: undefined};
		}
		if (objectInputs.inputRg.valueInput === person.rg) {
			document.querySelector("#rg").classList.remove("active-input-alterado");
			objectInputs.inputRg = {typeInput: undefined, valueInput: undefined};
		}

		if (openModal == true) {
			if (objectInputs.inputFirstName.typeInput === "firstName") {
				box.innerHTML += `
					<div class="confirmation-configuration-user-body-comparation-container">
						<p class="p-info-comparation">Nome:</p>
						<div class="flex-contianer">
							<div class="confirmation-configuration-user-body-comparation antes">
								<label for="cpf">Antes</label>
								<input type="text" id="cpf" value="${person.firstName}" disabled>
							</div>
							<div class="confirmation-configuration-user-body-comparation depois">
								<label for="cpf">Depois</label>
								<input type="text" id="cpf" value="${objectInputs.inputFirstName.valueInput}" disabled>
							</div>
						</div>
					</div>
				`;
			}

			if (objectInputs.inputLastName.typeInput === "lastName") {
				box.innerHTML += `
					<div class="confirmation-configuration-user-body-comparation-container">
						<p class="p-info-comparation">Sobrenome:</p>
						<div class="flex-contianer">
							<div class="confirmation-configuration-user-body-comparation antes">
								<label for="cpf">Antes</label>
								<input type="text" id="cpf" value="${person.lastName}" disabled>
							</div>
							<div class="confirmation-configuration-user-body-comparation depois">
								<label for="cpf">Depois</label>
								<input type="text" id="cpf" value="${objectInputs.inputLastName.valueInput}" disabled>
							</div>
						</div>
					</div>
				`;
			}

			if (objectInputs.inputBirthDate.typeInput === "birthDate") {
				box.innerHTML += `
					<div class="confirmation-configuration-user-body-comparation-container">
						<p class="p-info-comparation">Data de nascimento:</p>
						<div class="flex-contianer">
							<div class="confirmation-configuration-user-body-comparation antes">
								<label for="cpf">Antes</label>
								<input type="text" id="cpf" value="${person.birthDate}" disabled>
							</div>
							<div class="confirmation-configuration-user-body-comparation depois">
								<label for="cpf">Depois</label>
								<input type="text" id="cpf" value="${objectInputs.inputBirthDate.valueInput}" disabled>
							</div>
						</div>
					</div>
				`;
			}

			if (objectInputs.inputEmail.typeInput === "email") {
				box.innerHTML += `
					<div class="confirmation-configuration-user-body-comparation-container">
						<p class="p-info-comparation">Email:</p>
						<div class="flex-contianer">
							<div class="confirmation-configuration-user-body-comparation antes">
								<label for="cpf">Antes</label>
								<input type="text" id="cpf" value="${person.email}" disabled>
							</div>
							<div class="confirmation-configuration-user-body-comparation depois">
								<label for="cpf">Depois</label>
								<input type="text" id="cpf" value="${objectInputs.inputEmail.valueInput}" disabled>
							</div>
						</div>
					</div>
				`;
			}

			if (objectInputs.inputPhone.typeInput === "phone") {
				box.innerHTML += `
					<div class="confirmation-configuration-user-body-comparation-container">
						<p class="p-info-comparation">Telefone:</p>
						<div class="flex-contianer">
							<div class="confirmation-configuration-user-body-comparation antes">
								<label for="cpf">Antes</label>
								<input type="text" id="cpf" value="${person.phone}" disabled>
							</div>
							<div class="confirmation-configuration-user-body-comparation depois">
								<label for="cpf">Depois</label>
								<input type="text" id="cpf" value="${objectInputs.inputPhone.valueInput}" disabled>
							</div>
						</div>
					</div>
				`;
			}

			if (objectInputs.inputAddress.typeInput === "address") {
				box.innerHTML += `
					<div class="confirmation-configuration-user-body-comparation-container">
						<p class="p-info-comparation">Endereço:</p>
						<div class="flex-contianer">
							<div class="confirmation-configuration-user-body-comparation antes">
								<label for="cpf">Antes</label>
								<input type="text" id="cpf" value="${person.address}" disabled>
							</div>
							<div class="confirmation-configuration-user-body-comparation depois">
								<label for="cpf">Depois</label>
								<input type="text" id="cpf" value="${objectInputs.inputAddress.valueInput}" disabled>
							</div>
						</div>
					</div>
				`;
			}

			if (objectInputs.inputComplement.typeInput === "complement") {
				box.innerHTML += `
					<div class="confirmation-configuration-user-body-comparation-container">
						<p class="p-info-comparation">Complemento:</p>
						<div class="flex-contianer">
							<div class="confirmation-configuration-user-body-comparation antes">
								<label for="cpf">Antes</label>
								<input type="text" id="cpf" value="${person.complement}" disabled>
							</div>
							<div class="confirmation-configuration-user-body-comparation depois">
								<label for="cpf">Depois</label>
								<input type="text" id="cpf" value="${objectInputs.inputComplement.valueInput}" disabled>
							</div>
						</div>
					</div>
				`;
			}

			if (objectInputs.inputNumber.typeInput === "number") {
				box.innerHTML += `
					<div class="confirmation-configuration-user-body-comparation-container">
						<p class="p-info-comparation">Número:</p>
						<div class="flex-contianer">
							<div class="confirmation-configuration-user-body-comparation antes">
								<label for="cpf">Antes</label>
								<input type="text" id="cpf" value="${person.number}" disabled>
							</div>
							<div class="confirmation-configuration-user-body-comparation depois">
								<label for="cpf">Depois</label>
								<input type="text" id="cpf" value="${objectInputs.inputNumber.valueInput}" disabled>
							</div>
						</div>
					</div>
				`;
			}

			if (objectInputs.inputNationality.typeInput === "nationality") {
				box.innerHTML += `
					<div class="confirmation-configuration-user-body-comparation-container">
						<p class="p-info-comparation">Nacionalidade:</p>
						<div class="flex-contianer">
							<div class="confirmation-configuration-user-body-comparation antes">
								<label for="cpf">Antes</label>
								<input type="text" id="cpf" value="${person.nationality}" disabled>
							</div>
							<div class="confirmation-configuration-user-body-comparation depois">
								<label for="cpf">Depois</label>
								<input type="text" id="cpf" value="${objectInputs.inputNationality.valueInput}" disabled>
							</div>
						</div>
					</div>
				`;
			}

			if (objectInputs.inputCpf.typeInput === "cpf") {
				box.innerHTML += `
					<div class="confirmation-configuration-user-body-comparation-container">
						<p class="p-info-comparation">Cpf:</p>
						<div class="flex-contianer">
							<div class="confirmation-configuration-user-body-comparation antes">
								<label for="cpf">Antes</label>
								<input type="text" id="cpf" value="${person.cpf}" disabled>
							</div>
							<div class="confirmation-configuration-user-body-comparation depois">
								<label for="cpf">Depois</label>
								<input type="text" id="cpf" value="${objectInputs.inputCpf.valueInput}" disabled>
							</div>
						</div>
					</div>
				`;
			}

			if (objectInputs.inputRg.typeInput === "rg") {
				box.innerHTML += `
					<div class="confirmation-configuration-user-body-comparation-container">
						<p class="p-info-comparation">Rg:</p>
						<div class="flex-contianer">
							<div class="confirmation-configuration-user-body-comparation antes">
								<label for="cpf">Antes</label>
								<input type="text" id="cpf" value="${person.rg}" disabled>
							</div>
							<div class="confirmation-configuration-user-body-comparation depois">
								<label for="cpf">Depois</label>
								<input type="text" id="cpf" value="${objectInputs.inputRg.valueInput}" disabled>
							</div>
						</div>
					</div>
				`;
			}

			if (box.innerHTML == "") {
				buttonConfirmDadosPessoais.style.display = "none";
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
						email: user.email,
						password: user.password,
						userStatus: user.userStatus,
						userAdmin: user.userAdmin,
						person: {
							id: user.person.id,
							firstName: document.querySelector("#firstName").value,
							lastName: document.querySelector("#lastName").value,
							birthDate: document.querySelector("#birthDate").value,
							email: document.querySelector("#email").value,
							phone: document.querySelector("#phone").value,
							address: document.querySelector("#address").value,
							complement: document.querySelector("#complement").value,
							number: document.querySelector("#number").value,
							nationality: document.querySelector("#nationality").value,
							gender: document.querySelector("#gender").value,
							cpf: document.querySelector("#cpf").value,
							rg: document.querySelector("#rg").value
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
							buttonConfirmDadosPessoais.style.display = "none";

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
		}
	});
});

document.querySelector(".button-exit-modal-confirmation-configuration-dados-pessoais").addEventListener('click', () => {
	const fade = document.querySelector(".fade");
	const containerConfirm = document.querySelector(".confirmation-configuration-user");

	fade.classList.remove("active");
	containerConfirm.classList.remove("active");

	box.innerHTML = "";
});