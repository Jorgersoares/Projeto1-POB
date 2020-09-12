package facade;

import java.util.List;
import java.text.DecimalFormat;
import dao.DAO;
import dao.DAO_Game;
import dao.DAO_Genre;
import dao.DAO_User;
import model.Game;
import model.Genre;
import model.User;

public class Facade {
	private static DAO_Game daoGame = new DAO_Game();
	private static DAO_User daoUser = new DAO_User();
	private static DAO_Genre daoGenre = new DAO_Genre();
	private static User user_logged;
	
	public static void start() {
		DAO.open();
	}
	
	public static void finish() {
		DAO.close();
	}
		
	public static User findUser(String email) throws Exception {
		User user = daoUser.read(email);
		
		if(user == null) {
			throw new Exception("Este usuário não existe.");
		}
		
		return user;
	}
	
	public static void registerGame(Game obj, String[] genresArray) throws Exception {
		DAO.begin();
		String gameTitle = obj.getTitle();
		
		Game g = daoGame.read(gameTitle);
		
		if(g != null) {
			DAO.rollback();
			throw new Exception(String.format("This game has already been registered: %s", gameTitle));
		}

		for(String x: genresArray) {
			Genre genre = daoGenre.read(x);
			
			if(genre == null) {
				DAO.rollback();
				throw new Exception(
						String.format("The genre %s does not exists. %s wasn't registered.", x, gameTitle)
					);
			}
			
			List<Genre> genres = obj.getGenres();
			genres.add(genre);
			
			obj.setGenres(genres);
		}
		
		daoGame.create(obj);
		DAO.commit();
		System.out.println(String.format("The game was registered successfully.", gameTitle));
	}
	
	public static void registerUser(String name, String email, String password) throws Exception {
		DAO.begin();
		
		User newUser = new User(name, email, password);
		
		User storagedUser = daoUser.read(email);
		
		if(storagedUser != null) {
			throw new Exception(String.format("This user already exists: %s", storagedUser.getName()));
		}
		
		daoUser.create(newUser);	
		DAO.commit();
		System.out.println("Usuário registrado com sucesso.");
	}
	
	public static void registerGenre(Genre obj) throws Exception {
		DAO.begin();
		String genreName = obj.getName();
		
		Genre genre = daoGenre.read(genreName);
		
		if(genre != null) {
			DAO.rollback();
			throw new Exception(String.format("This genre already exists: %s", genreName));
		}
		
		daoGenre.create(obj);	
		DAO.commit();
		System.out.println("The genre was registered successfully.");
	}
	
	public static void updateGame(String name) throws Exception {
		DAO.begin();
		
		Game game = daoGame.read(name);
		
		if(game == null) {
			throw new Exception(String.format("This game does not exist"));
		}
		
		game.setTitle("Teste de update");
		daoGame.update(game);
		DAO.commit();
	}
	
	public static void deleteGame(String name) throws Exception{
		DAO.begin();
		
		Game game = daoGame.read(name);
		
		if(game == null) {
			throw new Exception(String.format("This game does not exist"));
		}
		
		daoGame.delete(game);
		DAO.commit();
	}
	
	public static void updateGenre(String name) throws Exception {
		// TODO
	}
	
	public static User getLoggedUser() {
		return user_logged;
	}
	
	public static List<Game> listAllGames() {
		List<Game> games = daoGame.readAll();
		return games;
	}
	
	public static List<User> listAllUsers() {
		return daoUser.readAll();
	}
	
	public static List<Genre> listAllGenres() {	
		return daoGenre.readAll();
	}
	public static void buyGame(String gameTitle, User user) throws Exception {
		DAO.begin();
		
		Game game = daoGame.read(gameTitle);
		
		if(game == null) {
			throw new Exception("Esse jogo não está no nosso catálogo.");
		}
		
		double userWallet = user.getWallet();
		double gamePrice = game.getPrice();
		
		if(userWallet < gamePrice) {
			throw new Exception("Você não tem dinheiro suficiente para comprar esse jogo. :(");
		}
		
		user.debitWalletMoney(gamePrice);
		user.addGame(game);
		daoUser.update(user);
		
		DAO.commit();
	}
	
	public static void addMoney(double value, User user) throws Exception {
		DAO.begin();
		
		if(user == null) {
			throw new Exception("You need to be logged to execute this action.");
		}
		
		user.addWalletMoney(value);
		daoUser.update(user);
		DAO.commit();
	}
	
	public static void login(String email, String password) throws Exception {
		User user = findUser(email);
		String userPassword = user.getPassword();
//		System.out.println(String.format("'%s' '%s'", password, userPassword));
		
		if(userPassword.compareTo(password) != 0) {
			throw new Exception("Senha incorreta. Tente novamente.");
		}
		
		System.out.println("\nLogando usuário...");
		user_logged = user;
	}
	
	public static void logout() {
		user_logged = null;
	}
	
	public static String formatMoney(double amount) {
		DecimalFormat df = new DecimalFormat("0.##");
		return df.format(amount);
	}
	
	public static List<Game> pricesAboveFifty(){
		return daoGame.priceAboveFifty();
	}
}
