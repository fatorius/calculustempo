import style from "./style.module.css";

function Navbar() {
	return (
		<div className={style.navbar}>
			<h1>CalcTempo</h1>

			<button>Login / Registrar</button>
		</div>
	);
}

export default Navbar;
