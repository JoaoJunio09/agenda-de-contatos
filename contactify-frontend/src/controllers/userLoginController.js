import { showDetailsFailure } from "../utils/detailsFailure.js";
import { UserService } from "../service/userService.js";

let id = undefined;
let admin = false;

document.addEventListener('DOMContentLoaded', () => {
	const buttonLogin = document.getElementById("button-login");

	if (buttonLogin) {
		buttonLogin.addEventListener('click', async () => {
			const email = document.querySelector("#email").value;
			const password = document.querySelector("#password").value;

			try {
				validationData(email, password);

				const listUsers = await UserService.findAll();

				listUsers.forEach(user => {
					if (user.email === email && user.password === password) {
						id = user.id;
						if (user.userAdmin === "ALLOWED") {
							admin = true;
						}
					}
				});
				
				if (id == undefined) {
					showDetailsFailure("Usuário não existe");
					return;
				}

				function processDayOfWeek() {
					const todayDate = new Date();
					return todayDate.getDay();
				}

				const dailyLogin = {
					id: null,
					user_id: id,
					day: processDayOfWeek(),
					date: null
				};

				await UserService.registerDailyLogin(dailyLogin);

				localStorage.setItem('userId', id);
				localStorage.setItem('userAdmin', admin);
				window.location.href = "../pages/gerenciarContatos.html";
			}
			catch (error) {
				showDetailsFailure(error.message);
			}
		});
	}
});

function validationData(email, password) {
	if (email == null || email == undefined || email == "") {
		throw new Error("E-mail inválido.");
	}

	if (password == null || password == undefined || password == "") {
		throw new Error("Senha inválida.");
	}

	var emailRegex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/i;

	if (!emailRegex.test(email)) {
		throw new Error("Informe um e-mail válido.");
    }
}