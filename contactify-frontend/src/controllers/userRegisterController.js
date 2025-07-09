import { UserService } from "../service/userService.js";
import { User } from "../models/userModel.js";
import { Person } from "../models/personModal.js";
import { openMessageFailureRequest, closeMessageFailureRequest } from "../utils/requestAndOthersfailure.js";
import { showDetailsFailure } from "../utils/detailsFailure.js";
import { openMessageSuccess } from "../utils/messageSuccess.js";

const buttonRegisterUser = document.getElementById("btn-register");

let user;
let person;

buttonRegisterUser.addEventListener('click', async () => {
	try {
		const user = getData();
		
		const data = await create(user);
		localStorage.setItem("userId", data.id);
	}
	catch (error) {
		console.log(error);
		showDetailsFailure(error);
	}
});

function getData() {
	const firstName = document.querySelector("#first-name-register").value;
	const lastName = document.querySelector("#last-name-register").value;
	const birthDate = document.querySelector("#birth-date-register").value;
	const email = document.querySelector("#email-register").value;
	const phone = document.querySelector("#phone-register").value;
	const address = document.querySelector("#address-register").value;
	const complement = document.querySelector("#complement-register").value;
	const number = document.querySelector("#number-register").value;
	const nationality = document.querySelector("#nationality-register").value;
	let gender = document.querySelector("#gender-register").value;
	const cpf = document.querySelector("#cpf-register").value;
	const rg = document.querySelector("#rg-register").value;
	const emailAccount = document.querySelector("#email-account-register").value;
	const passwordAccount  = document.querySelector("#password-account-register").value;
	const statusAccount = document.querySelector("#status-account-register").value;
	const adminAccount = document.querySelector("#admin-account-register").value;

	try {
		validationAllData(email, emailAccount, phone, cpf, rg, address, passwordAccount);
	}
	catch (error) {
		throw error;
	}
	
	if (gender === "female") {
		gender = "Feminino";
	}
	else {
		gender = "Masculino";
	}

	person = new Person();
	person.setDataFormCreate(
		null,
		firstName,
		lastName,
		birthDate,
		email,
		phone,
		gender,
		address,
		complement,
		number,
		nationality,
		cpf,
		rg
	);

	user = new User(
		null,
		emailAccount,
		passwordAccount,
		person,
		statusAccount,
		adminAccount
	);

	return user;
}

function validationAllData(email, emailAccount, phone, cpf, rg, address, password) {
	var emailRegex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/i;
    var phoneRegex = /^(?:(?:\+|00)?(55)\s?)?(?:\(?([1-9][0-9])\)?\s?)?(?:((?:9\d|[2-9])\d{3})\-?(\d{4}))$/;
	var cpfRegex = /^(\d{3}\.?\d{3}\.?\d{3}-?\d{2})$/;
	var rgRegex = /^\d{1,2}\.?\d{3}\.?\d{3}-?[0-9Xx]$/;
	var addressRegex = /^[A-Za-zÀ-ÿ0-9\s.,º°\-']{3,100}$/;
	var passwordRegex = /^(?=.*[a-z])(?=.*\d).{8,}$/;
	let errorMessage = "";

	if (!emailRegex.test(email) && emailRegex.test(emailAccount)) {
		errorMessage = "Preencha um e-mail válido.";
		throw errorMessage;
	}

	if (!phoneRegex.test(phone)) {
		errorMessage = "Informe um telefone válido.";
		throw errorMessage;
	}

	if (!cpfRegex.test(cpf)) {
		errorMessage = "CPF deve conter 11 dígitos";
		throw errorMessage;
	}

	if (!rgRegex.test(rg)) {
		errorMessage = "RG deve conter 9 dígitos";
		throw errorMessage;
	}

	if (!addressRegex.test(address)) {
		errorMessage = "Informe um endereço válido";
		throw errorMessage;
	}

	if (!passwordRegex.test(password)) {
		errorMessage = "Senha muito fraca";
		throw errorMessage;
	}
}

async function create(user) {
	try {
		const data = await UserService.create(user);
		openMessageSuccess("Cadastrado com sucesso.");

		setTimeout(() => {
			window.location.href = "../pages/loginUsuarios.html";
		}, 12000);

		return data;
	}
	catch (error) {
		throw Error("Erro crítico ao cadastrar usuário");
	}
}