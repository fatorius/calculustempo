import getRanking from "@/requests/getRanking";
import style from "./style.module.css";

export default async function Home() {
	const ranking = await getRanking();

	return (
		<div className={style.homepage}>
			<div></div>

			<div className={style.ranking}>
				<h2>Ranking</h2>

				<div>
					{ranking.map((entry, index) => {
						return (
							<div
								key={index}
								className={style.ranking_table_row}>
								<p>{index + 1}.</p>
								<p>{entry.username}</p>
								<p>{entry.rating}</p>
							</div>
						);
					})}
				</div>
			</div>
		</div>
	);
}
