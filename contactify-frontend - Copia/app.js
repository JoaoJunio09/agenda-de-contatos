document.addEventListener('DOMContentLoaded', () => {
  	const savedTheme = localStorage.getItem('theme');

  	if (savedTheme) {
    	document.documentElement.className = savedTheme;
  	} 
  	else {
		if (window.innerWidth < 1024) {
			document.documentElement.className = 'theme-dark';
			localStorage.setItem('theme', 'theme-dark');
		}
		else {
			document.documentElement.className = 'theme-light';
			localStorage.setItem('theme', 'theme-light');
		}
  	}
});
