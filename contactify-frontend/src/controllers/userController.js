import { showDetailsFailure, showPasswordInvalid } from "../utils/detailsFailure.js";
import { openMessageFailureRequest, closeMessageFailureRequest } from "../utils/requestAndOthersfailure.js";
import { User } from "../models/userModel.js";
import { Person } from "../models/personModal.js";
import { UserService } from "../service/userService.js";

const fade = document.querySelector("#fade");
const btnsCloseModalEditUser = document.querySelectorAll(".btn-close-modal-edit-user");
const modalEdit = document.querySelector(".modal-edit-user");

let userId = null;
let personId = null;
document.querySelector(".table-body").addEventListener('click', async (event) => {
	const btn = event.target.closest(".btn-open-modal-edit-user");
	const row = event.target.closest("tr");
	userId = row.getAttribute("data-id");
	console.log(userId);

	if (!btn || !row) return;

	try {
		const user = await findUserDetails(userId);
		
		document.querySelector("#id-person").value = user.person.id;
		personId = document.querySelector("#id-person").value;

		fillInFields(user);
	}
	catch (error) {
		openMessageFailureRequest({ title: "Erro inesperado", body: "Não foi possível visualizar usuário" });
	}
});

document.querySelector(".card-mobile-content").addEventListener('click', async (event) => {
	const btn = event.target.closest(".btn-open-modal-edit-user");
	const cardBody = event.target.closest(".card-body");

	if (!btn || !cardBody) return;

	userId = cardBody.getAttribute("data-id");

	try {
		const user = await findUserDetails(userId);

		fillInFields(user);
	}
	catch (error) {
		openMessageFailureRequest({ title: "Erro inesperado", body: "Não foi possível visualizar usuário" });
	}
});

const userLogin = null;

export function identifyUserLogin() {
	if (!userLogin) {
		return userLogin;
	}

	return null;
}

function fillInFields(user) {
	document.querySelector("#id-person").value = user.person.id;
		personId = document.querySelector("#id-person").value;

		document.querySelector("#firstName").value = user.person.firstName;
		document.querySelector("#lastName").value = user.person.lastName;
		document.querySelector("#birthDate").value = user.person.birthDate.substring(0,10);
		document.querySelector("#email").value = user.person.email;
		document.querySelector("#phone").value = user.person.phone;
		
		const gender = document.querySelector("#gender");
		const genderFromBackend = user.person.gender;

		if (genderFromBackend === "Masculino") {
			gender.value = "male";
		}
		else if (genderFromBackend === "Feminino") {
			gender.value = "female";
		}
		else {
			gender.value = "";
		}

		document.querySelector("#address").value = user.person.address;
		document.querySelector("#complement").value = user.person.complement;
		document.querySelector("#number").value = user.person.number;
		document.querySelector("#nationality").value = user.person.nationality;
		document.querySelector("#cpf").value = user.person.cpf;
		document.querySelector("#rg").value = user.person.rg;

		document.querySelector("#email-account").value = user.email;
		document.querySelector("#password-account").value = user.password;

		const status = document.querySelector("#status-account");
		const statusBackend = user.userStatus;

		if (statusBackend === "ACTIVE") {
			status.value = "active";
		}
		else if (statusBackend === "INACTIVE") {
			status.value = "inactive";
		}
		else if (statusBackend === "BANNED") {
			status.value = "banned";
		}

		const admin = document.querySelector("#admin-account");

		if (user.userAdmin === "NOT_ALLOWED") {
			admin.value = "not_allowed";
		}
		else {
			admin.value = "allowed";
		}

		modalEdit.classList.add("show-modal-edit-user");
        fade.classList.add("show-fade");
}

async function findUserDetails(id) {
	try {
		const data = await UserService.findUserDetails(id);
		return data;
	}
	catch (error) {
		throw error;
	}
}

async function update(user) {
	try {
		const data = await UserService.update(user);
		return data;
	}
	catch (error) {
		throw error;
	}
}

btnsCloseModalEditUser.forEach(btn => {
	btn.addEventListener('click', () => {
		closeModal();
	});
});

function closeModal() {
    modalEdit.classList.remove("show-modal-edit-user");
    fade.classList.remove("show-fade");

	currentStage = 0;
}

const btnNextPage = document.getElementById("btn-next-page");
const btnPreviousPage = document.querySelector("#btn-previous-page");
const slider = document.querySelector(".slider");

let currentStage = 0;

document.addEventListener('DOMContentLoaded', () => {
	findAllUser();
});

async function findAllUser() {

	let NUMBER_PAGE = 0;
	let listUsers = undefined;
	let pageActual = 1;

	try {
		const tableBody = document.querySelector(".table-body");
		tableBody.innerHTML = "";

		const cardMobileContent = document.querySelector(".card-mobile-content");
		cardMobileContent.innerHTML = "";

		let data = await UserService.findAllPageable(NUMBER_PAGE, 8, "asc");

		if (data != null) {
			const containerElementForButtonsNextOrPreviousPage = document.querySelector(".box-buttons-card-and-table-user");
			const buttonNextPage = document.querySelector(".button-next-page");				
			const buttonPreviousPage = document.querySelector(".button-previous-page");
			const numberForPageActual = document.querySelector(".number-for-page-actual");

			numberForPageActual.innerHTML = pageActual;

			if (data.page.totalPages > 1) {
				buttonPreviousPage.style.opacity = (NUMBER_PAGE === 0) ? "0" : "1";
				buttonNextPage.style.opacity = (NUMBER_PAGE >= data.page.totalPages - 1) ? "0" : "1";

				buttonNextPage.addEventListener('click', async () => {
					pageActual++;
					numberForPageActual.innerHTML = pageActual;

					NUMBER_PAGE++;
					data = await UserService.findAllPageable(NUMBER_PAGE, 8, "asc");

					try {
						if (data._embedded.user == null || data._embedded.user == undefined) {
							return;
						}
					}
					catch (error) {
						showDetailsFailure("Todos os usuários foram requeridos.");
					}
					
					listUsers = data._embedded.user;

					tableBody.innerHTML = "";
					cardMobileContent.innerHTML = "";

					buttonPreviousPage.style.opacity = (NUMBER_PAGE === 0) ? "0" : "1";
					buttonNextPage.style.opacity = (NUMBER_PAGE >= data.page.totalPages - 1) ? "0" : "1";
					addsRequestDataToTheUserTableAndDard(listUsers, tableBody, cardMobileContent);
				});

				buttonPreviousPage.addEventListener('click', async () => {
					pageActual--;
					numberForPageActual.innerHTML = pageActual;

					NUMBER_PAGE--;
					data = await UserService.findAllPageable(NUMBER_PAGE, 8, "asc");

					try {
						if (data._embedded.user == null || data._embedded.user == undefined) {
							return;	
						}
					}
					catch (error) {
						showDetailsFailure("Todos os usuários foram requeridos.");
					}
					
					listUsers = data._embedded.user;

					tableBody.innerHTML = "";
					cardMobileContent.innerHTML = "";

					buttonPreviousPage.style.opacity = (NUMBER_PAGE === 0) ? "0" : "1";
					buttonNextPage.style.opacity = (NUMBER_PAGE >= data.page.totalPages - 1) ? "0" : "1";
					addsRequestDataToTheUserTableAndDard(listUsers, tableBody, cardMobileContent);
				});
			}
			else {
				containerElementForButtonsNextOrPreviousPage.style.display = "none";
			}
		}
		
		listUsers = data._embedded.user;
		
		addsRequestDataToTheUserTableAndDard(listUsers, tableBody, cardMobileContent);
	}
	catch (error) {
		openMessageFailureRequest({ title: "Erro inesperado", body: "Não foi possível listar os usuários." });
	}
}

function addsRequestDataToTheUserTableAndDard(data, tableBody, cardMobileContent) {
	try {
			data.forEach(user => {
			const row = document.createElement("tr");
			row.setAttribute("data-id", user.id);

			const cardBody = document.createElement("div");
			cardBody.classList.add("card-body");
			cardBody.setAttribute("data-id", user.id);

			let status = undefined;
			let classStyleStatusDesktop;
			let classStyleStatusMobile;
			if (user.userStatus === "ACTIVE") {
				status = "Ativo";
				classStyleStatusDesktop = "atividade-usuario-ativo";
				classStyleStatusMobile = "box-status-ativo";
			}
			else if (user.userStatus === "INACTIVE") {
				status = "Inativo";
				classStyleStatusDesktop = "atividade-usuario-inativo";
				classStyleStatusMobile = "box-status-inativo";
			}
			else {
				status = "Banido";
				classStyleStatusDesktop = "atividade-usuario-banido";
				classStyleStatusMobile = "box-status-banido";
			}

			row.innerHTML = `
				<td>${user.person.firstName}</td>
				<td>${user.email}</td>
				<td><div class="${classStyleStatusDesktop}">${status}</div></td>
				<td>
					<div>
						<button class="btn-open-modal-edit-user"><img src="../assets/img/editar (3).png" alt="" class="img-button-edit-user"></button>
						<button><img src="../assets/img/excluir (3).png" alt=""></button>
					</div>
				</td>
			`;

			cardBody.innerHTML = `
				<div class="img-title">
					<div class="div-img-title">
						<img src="../assets/img/homem (1).png" alt="">
					</div>
					<div>
						<h1>${user.person.firstName}</h1>
						<p>${user.email}</p>
					</div>
				</div>
				<div class="active-status">
					<div class="box-status">								
						<p class="${classStyleStatusMobile}">${status}</p>
					</div>
					<div class="box-buttons">
						<button class="btn-open-modal-edit-user">
							<img src="../assets/img/editar (3).png" alt="">
							Editar
						</button>
						<button>
							<img src="../assets/img/excluir (3).png" alt="">
							Excluir
						</button>
					</div>
				</div>
			`;

			tableBody.appendChild(row);
			cardMobileContent.appendChild(cardBody);
		});
	}
	catch (error) {
		throw error;
	}
}

let person = new Person();
let user = null;

btnNextPage.addEventListener('click', () => { 
	let processPage3 = false;

    try{
        let dataValid;

        if (currentStage === 0) {
            dataValid = validationPage1();
        }
        else if (currentStage === 1) {
            dataValid = validationPage2();
        }
        else if (currentStage === 2) {
            dataValid = validationPage3();
			processPage3 = true;
        }
        
        if (dataValid) {
            processData();

			if (processPage3) {
				user.id = Number.parseInt(userId);
				user.person.id = Number.parseInt(personId);

				if (user.userStatus === "active") {
					user.userStatus = 1;
				}
				else if (user.userStatus === "inactive") {
					user.userStatus = 2;
				}
				else {
					user.userStatus = 3;
				}

				if (user.userAdmin === "allowed") {
					user.userAdmin = 1;
				}
				else if (user.userAdmin === "not_allowed") {
					user.userAdmin = 2;
				}

				if (user.person.gender === "female") {
					user.person.gender = "Feminino";
				}
				else {
					user.person.gender = "Masculino";
				}

				const res = update(user);
				res
					.then(() => {
						closeModal();
						findAllUser();

						currentStage = 0;
					})
					.catch(error => {
						openMessageFailureRequest({ title: "Erro ao atualizar", body: "Não foi possível atualizar usuário." });
					})
			}

			currentStage++;

			if (currentStage <= 2) {
            	updateSlider();	
			}
        }
    }
    catch (errors) {
		if (Array.isArray(errors)) {
			errors.forEach(error => {
				if (error.type == "all") {
					showDetailsFailure(error.message);
				}
				if (error.type == "phone") {
					const inputPhone = document.querySelector("#phone");
					inputPhone.classList.add("input-error");
					showDetailsFailure(error.message);
				}
				if (error.type == "email") {
					const inputEmail = document.querySelector("#email");
					inputEmail.classList.add("input-error");
					showDetailsFailure(error.message);
				}
				if (error.type == "cpf") {
					const inputCpf = document.querySelector("#cpf");
					inputCpf.classList.add("input-error");
					showDetailsFailure(error.message);
				}
				if (error.type == "rg") {
					const inputRg = document.querySelector("#rg");
					inputRg.classList.add("input-error");
					showDetailsFailure(error.message);
				}
				if (error.type == "email-account") {
					const inputCpf = document.querySelector("#password-account");
					inputCpf.classList.add("input-error");
					showDetailsFailure(error.message);
				}
				if (error.type == "password-account") {
					const inputPassword = document.querySelector("#password-account");
					inputPassword.classList.add("input-error");
					showPasswordInvalid(error.message);
				}
			});
		}
		else {
			showDetailsFailure("Erro inesperado");
		}
    }
});

btnPreviousPage.addEventListener('click', () => {
    if (currentStage > 0) {
		processData();

        currentStage--;
        updateSlider();
    }
});

updateSlider();
function updateSlider() {
    let percentage = currentStage * -100;
    slider.style.transform = `translateX(${percentage}%)`;
    
    btnPreviousPage.style.display = currentStage === 0 ? "none" : "initial";
}

let errors = [];

function validationPage1() {
    var emailRegex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/i;
    var phoneRegex = /^(?:(?:\+|00)?(55)\s?)?(?:\(?([1-9][0-9])\)?\s?)?(?:((?:9\d|[2-9])\d{3})\-?(\d{4}))$/;

    const inputsPage1 = document.querySelectorAll(".form-1 input");
	const email = document.querySelector("#email").value;
	const phone = document.querySelector("#phone").value;

	let hasEmptyField = false;
	errors = errors.filter(error => error.type !== "all");

    inputsPage1.forEach(input => {
        if (input.value == undefined || input.value == null || input.value == "") {		
			input.classList.add("input-error");
			hasEmptyField = true;
		}
		else {
			input.classList.remove("input-error");
		}
    });

	if (hasEmptyField) {
		errors.push({ type: "all", message: "Preencha todos os campos." });
	}

    if (emailRegex.test(email)) {
        errors = errors.filter(error => error.type !== "email");
		const inputEmail = document.querySelector("#email");
		inputEmail.classList.remove("input-error");
    }
    else {
        const errorExist = errors.some(error => error.type === "email");

        if (!errorExist) {
            errors.push({ type: "email", message: "Informe um email válido." });
        }
    }

    if (phoneRegex.test(phone)) {
        errors = errors.filter(error => error.type !== "phone");
		const inputPhone = document.querySelector("#phone");
		inputPhone.classList.remove("input-error");
    }
    else {
        const errorExist = errors.some(error => error.type === "phone");

        if (!errorExist) {
            errors.push({ type: "phone", message: "Informe um telefone válido." });
        }
    }

	if (errors.length > 0) {
		throw errors;
	}
   
    return true;
}

function validationPage2() {
	var cpfRegex = /^(\d{3}\.?\d{3}\.?\d{3}-?\d{2})$/;
	var rgRegex = /^\d{1,2}\.?\d{3}\.?\d{3}-?[0-9Xx]$/;
	var addressRegex = /^[A-Za-zÀ-ÿ0-9\s.,º°\-']{3,100}$/;

    const inputsPage2 = document.querySelectorAll(".form-2 input");
	const cpf = document.querySelector("#cpf").value;
	const rg = document.querySelector("#rg").value;
	const address = document.querySelector("#address").value;

	let hasEmptyField = false;
	errors = errors.filter(error => error.type !== "all");

    inputsPage2.forEach(input => {
        if (input.value == undefined || input.value == null || input.value == "") {		
			input.classList.add("input-error");
			hasEmptyField = true;
		}
		else {
			input.classList.remove("input-error");
		}
    });

	if (hasEmptyField) {
		errors.push({ type: "all", message: "Preencha todos os campos." });
	}

	if (cpfRegex.test(cpf)) {
		errors = errors.filter(error => error.type !== "cpf");
		const inputCpf = document.querySelector("#cpf");
		inputCpf.classList.remove("input-error");
	}
	else {
		const errorExist = errors.some(error => error.type === "cpf");

		if (!errorExist) {
			errors.push({ type: "cpf", message: "Informe um cpf válido." });
		}
	}

	if (rgRegex.test(rg)) {
		errors = errors.filter(error => error.type !== "rg");
		const inputRg = document.querySelector("#rg");
		inputRg.classList.remove("input-error");
	}
	else {
		const errorExist = errors.some(error => error.type === "rg");

		if (!errorExist) {
			errors.push({ type: "rg", message: "Informe um rg válido." });
		}
	}

	if (addressRegex.test(address)) {
		errors = errors.filter(error => error.type !== "address");
	}
	else {
		const errorExist = errors.some(error => error.type === "address");

		if (!errorExist) {
			errors.push({ type: "address", message: "Informe um endereço válido." });
		}
	}

	if (errors.length > 0) {
		throw errors;
	}

	return true;
}

function validationPage3() {
	var emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    var passwordRegex = /^(?=.*[a-z])(?=.*\d).{8,}$/;

    const inputsPage3 = document.querySelectorAll(".form-3 input");
	const emailAccount = document.querySelector("#email-account").value;
	const passwordAccount = document.querySelector("#password-account").value;

	let hasEmptyField = false;
	errors = errors.filter(error => error.type !== "all");

    inputsPage3.forEach(input => {
        if (input.value == undefined || input.value == null || input.value == "") {		
			input.classList.add("input-error");
			hasEmptyField = true;
		}
		else {
			input.classList.remove("input-error");
		}
    });

	if (hasEmptyField) {
		errors.push({ type: "all", message: "Preencha todos os campos." });
	}

	if (emailRegex.test(emailAccount)) {
		errors = errors.filter(error => error.type !== "email-account");
		const inputEmailAccount = document.querySelector("#email-account");
		inputEmailAccount.classList.remove("input-error");
	}
	else {
		const errorExist = errors.some(error => error.type === "email-account");

		if (!errorExist) {
			errors.push({ type: "email-account", message: "Informe um email válido." });
		}
	}

	if (passwordRegex.test(passwordAccount)) {
		errors = errors.filter(error => error.type !== "password-account");
		const inputCpf = document.querySelector("#password-account");
		inputCpf.classList.remove("input-error");
	}
	else {
		const errorExist = errors.some(error => error.type === "password-account");

		if (!errorExist) {
			errors.push({ type: "password-account", message: "Senha Inválida." });
		}
	}

	if (errors.length > 0) {
		throw errors;
	}

	return true;
}

function getDataFormPage1() {
	const firstName = document.getElementById("firstName").value;
	const lastName  = document.getElementById("lastName").value;
	const birthDate = document.getElementById("birthDate").value;
	const email     = document.getElementById("email").value;
	const phone     = document.getElementById("phone").value;
	const gender    = document.getElementById("gender").value;

    person.setDataFormPage1(
		null,
        firstName, 
        lastName, 
        birthDate, 
        email, 
        phone, 
        gender
    );
}

function getDataFormPage2() {
	const address     = document.getElementById("address").value;
	const complement  = document.getElementById("complement").value;
	const number      = document.getElementById("number").value;
	const nationality = document.getElementById("nationality").value;
	const cpf         = document.getElementById("cpf").value;
	const rg          = document.getElementById("rg").value;

    person.setDataFormPage2(
        address,
        complement,
        number,
        nationality,
        cpf,
        rg
    );
}

function getDataFormPage3() {
	const emailAccount    = document.getElementById("email-account").value;
	const passwordAccount = document.getElementById("password-account").value;
	const adminAccount    = document.getElementById("admin-account").value;
	const statusAccount   = document.getElementById("status-account").value;

    user = new User(
        null,
        emailAccount,
        passwordAccount,
		person,
		statusAccount,
        adminAccount,        
    );
}

function processData() {
	if (currentStage == 0) {
		getDataFormPage1();
	}
	else if (currentStage == 1) {
		getDataFormPage2();
	}
	else {
		getDataFormPage3();
	}
}