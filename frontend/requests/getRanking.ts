import { BACKEND_BASE_URL } from "@/consts";

interface RankingEntry {
	username: string;
	rating: number;
}

async function getRanking() {
	return new Promise<RankingEntry[]>((resolve, reject) => {
		fetch(`${BACKEND_BASE_URL}/ranking`)
			.then((obj) => {
				return obj.json();
			})
			.then((obj: { success: boolean; data: RankingEntry[] }) => {
				if (!obj.success) {
					reject();
				}
				resolve(obj.data);
			});
	});
}

export default getRanking;
