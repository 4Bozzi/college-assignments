package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * 
 * @author Super Kewl Team: Matt Giardina, Paul Frost, Steven Downs, Andrew
 *         Bozzi
 * 
 *  Assigned to: Andrew
 * 
 * This is the continent class awards bonuses for being occupied by one player.
 * This class also organizes the board into a sub-group smaller in number than
 * territories.
 * 
 * Methods contained: fullContinent(), name()
 */
public class Continent extends Observable {
	private ArrayList<Territory> territories = new ArrayList<Territory>();
	private int continentID;


	public Continent(int continentNum) {
		Territory alaska = new Territory("alaska");
		Territory nwTerritory = new Territory("nmTerritory");
		Territory greenland = new Territory("greenland");
		Territory alberta = new Territory("alberta");
		Territory ontario = new Territory("ontario");
		Territory quebec = new Territory("quebec");
		Territory westernUS = new Territory("westernUS");
		Territory easternUS = new Territory("easternUS");
		Territory centralAmerica = new Territory("centralAmerica");
		Territory venezuela = new Territory("venezuela");
		Territory peru = new Territory("peru");
		Territory brazil = new Territory("brazil");
		Territory argentina = new Territory("argentina");
		Territory iceland = new Territory("iceland");
		Territory scandinavia = new Territory("scandinavia");
		Territory ukraine = new Territory("ukraine");
		Territory greatBritain = new Territory("greatBritain");
		Territory nEurope = new Territory("nEurope");
		Territory wEurope = new Territory("wEurope");
		Territory sEurope = new Territory("sEurope");
		Territory nAfrica = new Territory("nAfrica");
		Territory egypt = new Territory("egypt");
		Territory eAfrica = new Territory("eAfrica");
		Territory cAfrica = new Territory("cAfrica");
		Territory sAfrica = new Territory("sAfrica");
		Territory madagascar = new Territory("magagascar");
		Territory ural = new Territory("ural");
		Territory siberia = new Territory("siberia");
		Territory yakutsk = new Territory("yakutsk");
		Territory kamchatka = new Territory("kamchatka");
		Territory afghanistan = new Territory("afghanistan");
		Territory irkutsk = new Territory("irkutsk");
		Territory mongolia = new Territory("mongolia");
		Territory japan = new Territory("japan");
		Territory middleEast = new Territory("middleEaster");
		Territory india = new Territory("india");
		Territory china = new Territory("china");
		Territory siam = new Territory("siam");
		Territory indonesia = new Territory("indonesia");
		Territory newGuinea = new Territory("newGuinea");
		Territory wAustralia = new Territory("wAustralia");
		Territory eAustralia = new Territory("eAstralia");

		// north america
		alaska.addAdjacentTerritory(nwTerritory);
		alaska.addAdjacentTerritory(alberta);
		alaska.addAdjacentTerritory(kamchatka);

		nwTerritory.addAdjacentTerritory(alaska);
		nwTerritory.addAdjacentTerritory(greenland);
		nwTerritory.addAdjacentTerritory(alberta);
		nwTerritory.addAdjacentTerritory(ontario);

		greenland.addAdjacentTerritory(nwTerritory);
		greenland.addAdjacentTerritory(ontario);
		greenland.addAdjacentTerritory(quebec);
		greenland.addAdjacentTerritory(iceland);
		
		quebec.addAdjacentTerritory(greenland);
		quebec.addAdjacentTerritory(ontario);
		quebec.addAdjacentTerritory(westernUS);
		quebec.addAdjacentTerritory(easternUS);
		
		alberta.addAdjacentTerritory(nwTerritory);
		alberta.addAdjacentTerritory(alaska);
		alberta.addAdjacentTerritory(ontario);
		alberta.addAdjacentTerritory(westernUS);

		ontario.addAdjacentTerritory(nwTerritory);
		ontario.addAdjacentTerritory(alberta);
		ontario.addAdjacentTerritory(quebec);
		ontario.addAdjacentTerritory(greenland);
		ontario.addAdjacentTerritory(easternUS);
		ontario.addAdjacentTerritory(westernUS);

		westernUS.addAdjacentTerritory(alberta);
		westernUS.addAdjacentTerritory(quebec);
		westernUS.addAdjacentTerritory(easternUS);
		westernUS.addAdjacentTerritory(centralAmerica);

		easternUS.addAdjacentTerritory(ontario);
		easternUS.addAdjacentTerritory(quebec);
		easternUS.addAdjacentTerritory(westernUS);
		easternUS.addAdjacentTerritory(centralAmerica);

		// south america
		centralAmerica.addAdjacentTerritory(westernUS);
		centralAmerica.addAdjacentTerritory(easternUS);
		centralAmerica.addAdjacentTerritory(venezuela);

		venezuela.addAdjacentTerritory(centralAmerica);
		venezuela.addAdjacentTerritory(peru);
		venezuela.addAdjacentTerritory(brazil);

		peru.addAdjacentTerritory(venezuela);
		peru.addAdjacentTerritory(brazil);
		peru.addAdjacentTerritory(argentina);

		brazil.addAdjacentTerritory(venezuela);
		brazil.addAdjacentTerritory(peru);
		brazil.addAdjacentTerritory(argentina);
		brazil.addAdjacentTerritory(nAfrica);

		argentina.addAdjacentTerritory(peru);
		argentina.addAdjacentTerritory(brazil);

		// europe
		iceland.addAdjacentTerritory(greenland);
		iceland.addAdjacentTerritory(scandinavia);
		iceland.addAdjacentTerritory(greatBritain);

		scandinavia.addAdjacentTerritory(iceland);
		scandinavia.addAdjacentTerritory(greatBritain);
		scandinavia.addAdjacentTerritory(nEurope);
		scandinavia.addAdjacentTerritory(ukraine);

		ukraine.addAdjacentTerritory(scandinavia);
		ukraine.addAdjacentTerritory(nEurope);
		ukraine.addAdjacentTerritory(sEurope);
		ukraine.addAdjacentTerritory(ural);
		ukraine.addAdjacentTerritory(afghanistan);
		ukraine.addAdjacentTerritory(middleEast);

		greatBritain.addAdjacentTerritory(iceland);
		greatBritain.addAdjacentTerritory(scandinavia);
		greatBritain.addAdjacentTerritory(nEurope);
		greatBritain.addAdjacentTerritory(wEurope);

		nEurope.addAdjacentTerritory(greatBritain);
		nEurope.addAdjacentTerritory(scandinavia);
		nEurope.addAdjacentTerritory(sEurope);
		nEurope.addAdjacentTerritory(ukraine);
		nEurope.addAdjacentTerritory(sEurope);

		wEurope.addAdjacentTerritory(greatBritain);
		wEurope.addAdjacentTerritory(sEurope);
		wEurope.addAdjacentTerritory(nEurope);
		wEurope.addAdjacentTerritory(nAfrica);

		sEurope.addAdjacentTerritory(nEurope);
		sEurope.addAdjacentTerritory(wEurope);
		sEurope.addAdjacentTerritory(nEurope);
		sEurope.addAdjacentTerritory(egypt);
		sEurope.addAdjacentTerritory(ukraine);
		sEurope.addAdjacentTerritory(middleEast); // not sure if there is a
		// connection here
		sEurope.addAdjacentTerritory(nAfrica); // not sure

		// africa
		nAfrica.addAdjacentTerritory(brazil);
		nAfrica.addAdjacentTerritory(wEurope);
		nAfrica.addAdjacentTerritory(sEurope); // not sure
		nAfrica.addAdjacentTerritory(egypt);
		nAfrica.addAdjacentTerritory(cAfrica);
		nAfrica.addAdjacentTerritory(eAfrica);

		egypt.addAdjacentTerritory(nAfrica);
		egypt.addAdjacentTerritory(sEurope);
		egypt.addAdjacentTerritory(middleEast);
		egypt.addAdjacentTerritory(eAfrica);

		eAfrica.addAdjacentTerritory(egypt);
		eAfrica.addAdjacentTerritory(middleEast);
		eAfrica.addAdjacentTerritory(nAfrica);
		eAfrica.addAdjacentTerritory(cAfrica);
		eAfrica.addAdjacentTerritory(sAfrica);
		eAfrica.addAdjacentTerritory(madagascar);

		cAfrica.addAdjacentTerritory(nAfrica);
		cAfrica.addAdjacentTerritory(eAfrica);
		cAfrica.addAdjacentTerritory(sAfrica);

		sAfrica.addAdjacentTerritory(cAfrica);
		sAfrica.addAdjacentTerritory(eAfrica);
		sAfrica.addAdjacentTerritory(madagascar);

		madagascar.addAdjacentTerritory(eAfrica);
		madagascar.addAdjacentTerritory(sAfrica);

		// asia
		ural.addAdjacentTerritory(china);
		ural.addAdjacentTerritory(afghanistan);
		ural.addAdjacentTerritory(siberia);
		ural.addAdjacentTerritory(ukraine);

		siberia.addAdjacentTerritory(ural);
		siberia.addAdjacentTerritory(yakutsk);
		siberia.addAdjacentTerritory(irkutsk);
		siberia.addAdjacentTerritory(mongolia);
		siberia.addAdjacentTerritory(china);

		yakutsk.addAdjacentTerritory(siberia);
		yakutsk.addAdjacentTerritory(irkutsk);
		yakutsk.addAdjacentTerritory(kamchatka);

		kamchatka.addAdjacentTerritory(yakutsk);
		kamchatka.addAdjacentTerritory(irkutsk);
		kamchatka.addAdjacentTerritory(mongolia);
		kamchatka.addAdjacentTerritory(japan);
		kamchatka.addAdjacentTerritory(alaska);

		afghanistan.addAdjacentTerritory(ukraine);
		afghanistan.addAdjacentTerritory(middleEast);
		afghanistan.addAdjacentTerritory(india);
		afghanistan.addAdjacentTerritory(china);
		afghanistan.addAdjacentTerritory(ural);

		irkutsk.addAdjacentTerritory(siberia);
		irkutsk.addAdjacentTerritory(yakutsk);
		irkutsk.addAdjacentTerritory(kamchatka);
		irkutsk.addAdjacentTerritory(mongolia);

		mongolia.addAdjacentTerritory(siberia);
		mongolia.addAdjacentTerritory(irkutsk);
		mongolia.addAdjacentTerritory(japan);
		mongolia.addAdjacentTerritory(kamchatka);
		mongolia.addAdjacentTerritory(china);

		japan.addAdjacentTerritory(kamchatka);
		japan.addAdjacentTerritory(mongolia);

		china.addAdjacentTerritory(afghanistan);
		china.addAdjacentTerritory(ural);
		china.addAdjacentTerritory(siberia);
		china.addAdjacentTerritory(mongolia);
		china.addAdjacentTerritory(india);
		china.addAdjacentTerritory(siam);

		middleEast.addAdjacentTerritory(ukraine);
		middleEast.addAdjacentTerritory(sEurope);
		middleEast.addAdjacentTerritory(egypt);
		middleEast.addAdjacentTerritory(eAfrica);
		middleEast.addAdjacentTerritory(afghanistan);
		middleEast.addAdjacentTerritory(india);

		india.addAdjacentTerritory(middleEast);
		india.addAdjacentTerritory(afghanistan);
		india.addAdjacentTerritory(china);
		india.addAdjacentTerritory(siam);

		siam.addAdjacentTerritory(china);
		siam.addAdjacentTerritory(india);
		siam.addAdjacentTerritory(indonesia);

		// finally australia
		indonesia.addAdjacentTerritory(siam);
		indonesia.addAdjacentTerritory(newGuinea);
		indonesia.addAdjacentTerritory(wAustralia);

		newGuinea.addAdjacentTerritory(indonesia);
		newGuinea.addAdjacentTerritory(wAustralia);
		newGuinea.addAdjacentTerritory(eAustralia);

		wAustralia.addAdjacentTerritory(newGuinea);
		wAustralia.addAdjacentTerritory(eAustralia);
		wAustralia.addAdjacentTerritory(indonesia);

		eAustralia.addAdjacentTerritory(newGuinea);
		eAustralia.addAdjacentTerritory(wAustralia);

		if (continentNum == 0) {
			territories.add(alaska);
			territories.add(nwTerritory);
			territories.add(greenland);
			territories.add(alberta);
			territories.add(ontario);
			territories.add(quebec);
			territories.add(westernUS);
			territories.add(easternUS);
			territories.add(centralAmerica);
			continentID = continentNum;
		}
		if (continentNum == 1) {
			territories.add(venezuela);
			territories.add(peru);
			territories.add(brazil);
			territories.add(argentina);
			continentID = continentNum;
		}
		if (continentNum == 2) {
			territories.add(iceland);
			territories.add(scandinavia);
			territories.add(ukraine);
			territories.add(greatBritain);
			territories.add(nEurope);
			territories.add(wEurope);
			territories.add(sEurope);
			continentID = continentNum;
		}
		if (continentNum == 3) {
			territories.add(nAfrica);
			territories.add(egypt);
			territories.add(eAfrica);
			territories.add(cAfrica);
			territories.add(sAfrica);
			territories.add(madagascar);
			continentID = continentNum;
		}
		if (continentNum == 4) {
			territories.add(ural);
			territories.add(siberia);
			territories.add(yakutsk);
			territories.add(kamchatka);
			territories.add(afghanistan);
			territories.add(irkutsk);
			territories.add(mongolia);
			territories.add(japan);
			territories.add(middleEast);
			territories.add(india);
			territories.add(china);
			territories.add(siam);
			continentID = continentNum;
		}
		if (continentNum == 5) {
			territories.add(indonesia);
			territories.add(newGuinea);
			territories.add(wAustralia);
			territories.add(eAustralia);
			continentID = continentNum;
		}
	}

	public List<Territory> getTerritories() {
		return territories;
	}


	public int getContinentID() {
		return continentID;
	}
}
