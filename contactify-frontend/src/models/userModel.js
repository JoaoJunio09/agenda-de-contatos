export class User {
	id = null;
	email = "";
	password = "";
	person = null;
	userStatus = "";
	userAdmin = "";

	constructor(id, email, password, person, userStatus, userAdmin) {
		this.id = null,
		this.email = email,
		this.password = password,
		this.person = person,
		this.userStatus = userStatus,
		this.userAdmin = userAdmin;
	};

	getUser() {
		const user = {
			id: this.id,
			email: this.email,
		    password: this.password,
			person: this.person,
			userStatus: this.userStatus,
			userAdmin: this.userAdmin,
		};
		
		return user;
	}
}