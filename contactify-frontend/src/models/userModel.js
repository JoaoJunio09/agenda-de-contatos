export class User {
	id = null;
	email = "";
	password = "";
	person = null;
	status = "";
	admin = "";

	constructor(id, email, password, person, status, admin) {
		this.id = null,
		this.email = email,
		this.password = password,
		this.person = person,
		this.status = status,
		this.admin = admin;
	};

	getUser() {
		const user = {
			id: this.id,
			email: this.email,
		    password: this.password,
			person: this.person,
			status: this.status,
			admin: this.admin,
		};
		
		return user;
	}
}