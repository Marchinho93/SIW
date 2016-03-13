package it.uniroma3.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.uniroma3.model.Prodotto;
import it.uniroma3.persistence.ProdottoDAO;
import it.uniroma3.persistence.DBOperations;

public class ProdottoDaoJDBC implements ProdottoDAO {


	public static void save(Prodotto prodotto) throws PersistenceException{
		Connection connection = DBOperations.getConnection();;
		String query = "INSERT into prodotto(id, nome, descrizione, prezzo) values (?,?,?,?)";
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			statement.setLong(1, prodotto.getId());
			statement.setString(2, prodotto.getNome());
			statement.setString(3, prodotto.getDescrizione());
			statement.setDouble(4, prodotto.getPrezzo());
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	
	public static void delete(Prodotto prodotto){
		Connection connection = DBOperations.getConnection();;
		String query = "DELETE from prodotto where id = ?";
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			statement.setLong(1, prodotto.getId());
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	
	public static void update(Prodotto prodotto){
		Connection connection = DBOperations.getConnection();;
		String query = "UPDATE prodotto SET nome=?, descrizione=?, prezzo=? where id=?";
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, prodotto.getNome());
			statement.setString(2, prodotto.getDescrizione());
			statement.setDouble(3, prodotto.getPrezzo());
			statement.setLong(4, prodotto.getId());
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	
	public static Prodotto findByPrimaryKey(Long id){
		Connection connection = DBOperations.getConnection();
		String query = "SELECT id,nome,descrizione,prezzo FROM prodotto WHERE id=?";
		PreparedStatement statement;
		try{
			statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			result.next();
			Prodotto p = new Prodotto(result.getLong("id"),result.getString("nome"),result.getString("descrizione"),result.getDouble("prezzo"));
			connection.close();
			return p;
		} catch (SQLException e){
			throw new PersistenceException(e.getMessage());
		}
	}
	
 	public static List<Prodotto> findAll(){
 		Connection connection = DBOperations.getConnection();
		String query = "SELECT id,nome,descrizione,prezzo FROM prodotto";
		PreparedStatement statement;
		try{
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			LinkedList<Prodotto> lp = new LinkedList<>(); 
			while(result.next()){
			Prodotto p = new Prodotto(result.getLong("id"),result.getString("nome"),result.getString("descrizione"),result.getDouble("prezzo"));
			lp.add(p);
			}
			connection.close();
			return lp;
		} catch (SQLException e){
			throw new PersistenceException(e.getMessage());
		}
 	}
}
