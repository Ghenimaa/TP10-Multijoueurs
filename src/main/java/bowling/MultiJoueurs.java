package bowling;

import java.util.ArrayList;
import java.util.List;

public class MultiJoueurs implements IPartieMultiJoueurs {
	private List<PartieMonoJoueur> partiesJoueurs;
	private String[] joueursList;
	private int indexJoueurActuel;

	@Override
	public String demarreNouvellePartie(String[] nomsDesJoueurs) {
		if (nomsDesJoueurs == null || nomsDesJoueurs.length == 0) {
			throw new IllegalArgumentException("Pas de joueur dans la partie");
		}
		partiesJoueurs = new ArrayList<>();
		for (String joueur : nomsDesJoueurs) {
			partiesJoueurs.add(new PartieMonoJoueur());
		}
		joueursList = nomsDesJoueurs;
		indexJoueurActuel = 0;
		return obtenirProchainTir(joueursList[indexJoueurActuel]);
	}

	@Override
	public String enregistreLancer(int nombreDeQuillesAbattues) {
		if (partiesJoueurs == null) {
			throw new IllegalStateException("La partie n'est pas démarrée");
		}

		if (toutesPartiesTerminees()) {
			return "Partie terminée";
		}

		String joueurActuel = joueursList[indexJoueurActuel];
		PartieMonoJoueur partieJoueur = partiesJoueurs.get(indexJoueurActuel);
		if (!partieJoueur.enregistreLancer(nombreDeQuillesAbattues)) {
			indexJoueurActuel = (indexJoueurActuel + 1) % joueursList.length;
		}

		return obtenirProchainTir(joueursList[indexJoueurActuel]);
	}

	@Override
	public int scorePour(String nomDuJoueur) {
		for (int i = 0; i < joueursList.length; i++) {
			if (joueursList[i].equals(nomDuJoueur)) {
				return partiesJoueurs.get(i).score();
			}
		}
		throw new IllegalArgumentException("Le joueur n'existe pas");
	}

	private String obtenirProchainTir(String joueur) {
		PartieMonoJoueur partie = partiesJoueurs.get(indexJoueurActuel);
		return String.format("Prochain tir : joueur %s, tour n° %d, boule n° %d",
			joueur, partie.numeroTourCourant(), partie.numeroProchainLancer());
	}

	private boolean toutesPartiesTerminees() {
		for (PartieMonoJoueur partieJoueur : partiesJoueurs) {
			if (!partieJoueur.estTerminee()) {
				return false;
			}
		}
		return true;
	}
}
