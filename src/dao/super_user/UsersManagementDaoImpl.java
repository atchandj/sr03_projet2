package dao.super_user;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import beans.EmailAccount;
import beans.User;
import beans.super_user.SuperUser;
import beans.trainee.Trainee;
import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;

public class UsersManagementDaoImpl implements UsersManagementDao{
	
	private static final String FILE_PROPERTIES = "/dao/email_account.properties";
	private static final String PROPERTY_LOGIN = "login";
	private static final String PROPERTY_PASSWORD = "password";
	private static final String HOST_NAME = "smtp.googlemail.com";
	private static final int SMTP_PORT = 465;
	
	private DaoFactory daoFactory;
    private EmailAccount emailAccount;

    public UsersManagementDaoImpl(DaoFactory daoFactory) throws DAOConfigurationException {
        this.daoFactory = daoFactory;
        
    	Properties properties = new Properties();
        String login = null;
        String password = null;
        
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fileProperties = classLoader.getResourceAsStream(FILE_PROPERTIES);

        if ( fileProperties == null ) {
            throw new DAOConfigurationException("Le fichier properties " + FILE_PROPERTIES + " est introuvable." );
        }

        try {
            properties.load( fileProperties );
            login = properties.getProperty( PROPERTY_LOGIN );
            password = properties.getProperty( PROPERTY_PASSWORD );
        } catch ( IOException e ) {
            throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FILE_PROPERTIES + ".");
        }
        this.emailAccount = new EmailAccount(login, password);
    }
    
    @Override
	public List<Trainee> getTrainees() throws DaoException{
        List<Trainee> trainees = new ArrayList<Trainee>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try{
            connexion = daoFactory.getConnection();
            // System.out.println("SELECT email, surname, name, phone, company, accountCreation, accountStatus FROM Trainee;");
            preparedStatement = (PreparedStatement) connexion.prepareStatement("SELECT email, surname, name, phone, company, accountCreation, accountStatus FROM Trainee;");
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Trainee tmpTrainee = new Trainee();
                
            	String email = result.getString("email");
                String surname = result.getString("surname");
                String name = result.getString("name");
                String phone = result.getString("phone");
                String company = result.getString("company");
                Timestamp accountCreation = result.getTimestamp("accountCreation");
                boolean accountStatus = result.getBoolean("accountStatus");

                tmpTrainee.setEmail(email);
                tmpTrainee.setSurname(surname);
                tmpTrainee.setName(name);
                tmpTrainee.setPhone(phone);
                tmpTrainee.setCompany(company);
                tmpTrainee.setAccountCreation(accountCreation);
                tmpTrainee.setAccountStatus(accountStatus);
                
                // System.out.println(tmpTrainee.getEmail()); // Test
                // System.out.println(tmpTrainee.getSurname()); // Test
                // System.out.println(tmpTrainee.getName()); // Test
                
                trainees.add(tmpTrainee);
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
        return trainees;
    }
    
    @Override
	public List<SuperUser> getSuperUsers(SuperUser superUser) throws DaoException{
        List<SuperUser> superUsers = new ArrayList<SuperUser>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String superUserEmail = superUser.getEmail();        
        try{
            connexion = daoFactory.getConnection();
            // System.out.println("SELECT email, surname, name, phone, company, accountCreation, accountStatus FROM SuperUser WHERE email <> ?;");
            preparedStatement = (PreparedStatement) connexion.prepareStatement("SELECT email, surname, name, phone, company, accountCreation, accountStatus FROM SuperUser WHERE email <> ?;");
            preparedStatement.setString(1, superUserEmail);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                SuperUser tmpSuperUser = new SuperUser();
            	
            	String email = result.getString("email");
                String surname = result.getString("surname");
                String name = result.getString("name");
                String phone = result.getString("phone");
                String company = result.getString("company");
                Timestamp accountCreation = result.getTimestamp("accountCreation");
                boolean accountStatus = result.getBoolean("accountStatus");

                tmpSuperUser.setEmail(email);
                tmpSuperUser.setSurname(surname);
                tmpSuperUser.setName(name);
                tmpSuperUser.setPhone(phone);
                tmpSuperUser.setCompany(company);
                tmpSuperUser.setAccountCreation(accountCreation);
                tmpSuperUser.setAccountStatus(accountStatus);
                
                // System.out.println(tmpSuperUser.getEmail()); // Test
                // System.out.println(tmpSuperUser.getSurname()); // Test
                // System.out.println(tmpSuperUser.getName()); // Test
                
                superUsers.add(tmpSuperUser);
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
        return superUsers;
    }    
    
    @Override
	public void dropTrainee(String email) throws DaoException{
    	// System.out.println("Supprimer stagiaire"); // Test
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try{
            connexion = daoFactory.getConnection();
            // System.out.println("DELETE FROM Trainee WHERE email =  ?;"); // Test
            preparedStatement = (PreparedStatement) connexion.prepareStatement("DELETE FROM Trainee WHERE email =  ?;");
            preparedStatement.setString(1, email);
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            // System.out.println(result); // Test
            if(result == 0){
            	// System.out.println("Stagiaire à supprimer inconnu."); // Test
            	throw new DaoException("Stagiaire à supprimer inconnu.");
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
    }
    
    @Override
	public void dropSuperSuper(String email) throws DaoException{
    	// System.out.println("Supprimer administrateur");
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try{
            connexion = daoFactory.getConnection();
            // System.out.println("DELETE FROM SuperUser WHERE email =  ?;");
            preparedStatement = (PreparedStatement) connexion.prepareStatement("DELETE FROM SuperUser WHERE email =  ?;");
            preparedStatement.setString(1, email);
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            // System.out.println(result);
            if(result == 0){
            	// System.out.println("Administrateur à supprimer inconnu.");
            	throw new DaoException("Administrateur à supprimer inconnu.");
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
    }
    
    @Override
    public void addTrainee(Trainee trainee) throws DaoException {
    	// System.out.println("Ajouter stagiaire"); // Test
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String message = null;
        try{
            connexion = daoFactory.getConnection();
            // System.out.println("INSERT INTO Trainee (email, surname, name, password, phone, company, accountCreation, accountStatus) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?);"); // Test
            preparedStatement = (PreparedStatement) connexion.prepareStatement("INSERT INTO Trainee (email, surname, name, password, phone, company, accountCreation, accountStatus) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?);");
            preparedStatement.setString(1, trainee.getEmail());
            preparedStatement.setString(2, trainee.getSurname());
            preparedStatement.setString(3, trainee.getName());
            preparedStatement.setString(4, trainee.getPassword());
            preparedStatement.setString(5, trainee.getPhone());
            preparedStatement.setString(6, trainee.getCompany());
            preparedStatement.setBoolean(7, trainee.isActive());
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            // System.out.println(result); // Test
            if(result == 0){
            	// System.out.println("Stagiaire impossible à ajouter."); // Test
            	throw new DaoException("Stagiaire impossible à ajouter.");
            }else{
        		message = "Bonjour Madame, Monsieur,\n\nUn compte stagiaire vient d'être créé en votre nom sur le site d'évaluation des stagiaires. " +
        		"Votre identifiant est : '" + trainee.getEmail() +"' et votre mot de passe est : '" + 
        		trainee.getPassword() + "'. ";
            	if(trainee.isActive()){
            		message += "Votre compte stagiaire est actif : vous pouvez dès à présent vous connecter et profiter des fonctionnalités du site,\n\n";    				
            	}else{
            		message += "Votre compte stagiaire est inactif : vous recevrez ultérieurement un message vous autorisant à vous connecter "+
            		"et à profiter des fonctionnalités du site,\n\n";
            	}
            	message += "Les administrateurs du site d'évaluation des stagiaires";
            	this.sendAnEmail("Création de compte stagiaire", message, trainee);
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
    }
    
    @Override
    public void addSuperUser(SuperUser superUser) throws DaoException {
    	// System.out.println("Ajouter administrateur"); // Test
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String message = null;
        try{
            connexion = daoFactory.getConnection();
            // System.out.println("INSERT INTO SuperUser (email, surname, name, password, phone, company, accountCreation, accountStatus) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?);"); // Test
            preparedStatement = (PreparedStatement) connexion.prepareStatement("INSERT INTO SuperUser (email, surname, name, password, phone, company, accountCreation, accountStatus) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?);");
            preparedStatement.setString(1, superUser.getEmail());
            preparedStatement.setString(2, superUser.getSurname());
            preparedStatement.setString(3, superUser.getName());
            preparedStatement.setString(4, superUser.getPassword());
            preparedStatement.setString(5, superUser.getPhone());
            preparedStatement.setString(6, superUser.getCompany());
            preparedStatement.setBoolean(7, superUser.isActive());
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            // System.out.println(result); // Test
            if(result == 0){
            	// System.out.println("Administrateur impossible à ajouter."); // Test
            	throw new DaoException("Administrateur impossible à ajouter.");
            }else{
        		message = "Bonjour Madame, Monsieur,\n\nUn compte administrateur vient d'être créé en votre nom sur le site d'évaluation des stagiaires. " +
        		"Votre identifiant est : '" + superUser.getEmail() +"' et votre mot de passe est : '" + 
        		superUser.getPassword() + "'. ";
            	if(superUser.isActive()){
            		message += "Votre compte administrateur est actif : vous pouvez dès à présent vous connecter et profiter des fonctionnalités du site,\n\n";    				
            	}else{
            		message += "Votre compte administrateur est inactif : vous recevrez ultérieurement un message vous autorisant à vous connecter "+
            		"et à profiter des fonctionnalités du site,\n\n";
            	}
            	message += "Les administrateurs du site d'évaluation des stagiaires";
            	this.sendAnEmail("Création de compte administrateur", message, superUser);
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
    }
    
    private void sendAnEmail(String subject, String message, User user) throws DaoException{
		Email email = new SimpleEmail();
		email.setHostName(HOST_NAME);
		email.setSmtpPort(SMTP_PORT);    	
		email.setAuthentication(this.emailAccount.getLogin(), this.emailAccount.getPassword());
		email.setDebug(true);
		email.setSSLOnConnect(true);
		email.setStartTLSEnabled(true);
		try {
			email.setFrom(this.emailAccount.getLogin());
			email.setSubject(subject);
			email.setMsg(message);
			email.addTo(user.getEmail());
			email.send();
		} catch (EmailException e) {
        	throw new DaoException("Impossible d'envoyer le mail : " + e.getMessage());
		}

    }
}