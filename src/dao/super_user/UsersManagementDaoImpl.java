package dao.super_user;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import beans.TemporaryUser;
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
    private TemporaryUser emailAccount;

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
        this.emailAccount = new TemporaryUser(login, password);
    }
    
    @Override
	public List<Trainee> getTrainees() throws DaoException{
        List<Trainee> trainees = new ArrayList<Trainee>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try{
            connexion = daoFactory.getConnection();
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
                tmpTrainee.setSurname(surname.toUpperCase());
                tmpTrainee.setName(name.substring(0, 1).toUpperCase() + name.substring(1));
                tmpTrainee.setPhone(phone);
                tmpTrainee.setCompany(company);
                tmpTrainee.setAccountCreation(accountCreation);
                tmpTrainee.setAccountStatus(accountStatus);
                
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
                tmpSuperUser.setSurname(surname.toUpperCase());
                tmpSuperUser.setName(name.substring(0, 1).toUpperCase() + name.substring(1));
                tmpSuperUser.setPhone(phone);
                tmpSuperUser.setCompany(company);
                tmpSuperUser.setAccountCreation(accountCreation);
                tmpSuperUser.setAccountStatus(accountStatus);
                
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
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String message = null;
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement("DELETE FROM Trainee WHERE email =  ?;");
            preparedStatement.setString(1, email);
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            if(result == 0){
            	throw new DaoException("Stagiaire à supprimer inconnu.");
            }else{
        		message = "Bonjour Madame, Monsieur,\n\nNous avons le regret de vous annoncer que votre compte stagiaire " + 
        		"ayant pour identifiant '" + email +"' vient d'avoir été supprimé,\n\n" +
            	"Les administrateurs du site d'évaluation des stagiaires";
            	this.sendAnEmail("Suppression de compte stagiaire", message, email);
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
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String message = null;
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement("DELETE FROM SuperUser WHERE email =  ?;");
            preparedStatement.setString(1, email);
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            if(result == 0){
            	throw new DaoException("Administrateur à supprimer inconnu.");
            }else{
        		message = "Bonjour Madame, Monsieur,\n\nNous avons le regret de vous annoncer que votre compte administrateur " + 
        		"ayant pour identifiant '" + email +"' vient d'avoir été supprimé,\n\n" +
            	"Les administrateurs du site d'évaluation des stagiaires";
            	this.sendAnEmail("Suppression de compte administrateur", message, email);
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
	public void modifyStatusTrainee(String email, boolean validate) throws DaoException{
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String message = null;
        String word1 = null;
        String word2 = null;
        String subject = null;
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement("UPDATE Trainee SET accountStatus = ? WHERE email = ?;");
            preparedStatement.setBoolean(1, validate);
            preparedStatement.setString(2, email);
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            if(result == 0){
            	throw new DaoException("Stagiaire à modifier inconnu.");
            }else{
        		if(validate){
        			word1 = "plaisir ";
        			word2 = "activé.\n\n";
        			subject = "Activation de compte stagiaire";
        		}else{
        			word1 = "regret ";
        			word2 = "désactivé.\n\n";
        			subject = "Désactivation de compte stagiaire";
        		}
        		message = "Bonjour Madame, Monsieur,\n\nNous avons le ";
        		message += word1;
        		message += "de vous annoncer que votre compte stagiaire ";
        		message += "ayant pour identifiant '" + email +"' vient d'avoir été ";
        		message += word2;
        		message += "Les administrateurs du site d'évaluation des stagiaires";
            	this.sendAnEmail(subject, message, email);
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
	public void modifyStatusSuperUser(String email, boolean validate) throws DaoException{
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String message = null;
        String word1 = null;
        String word2 = null;
        String subject = null;
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement("UPDATE SuperUser SET accountStatus = ? WHERE email = ?;");
            preparedStatement.setBoolean(1, validate);
            preparedStatement.setString(2, email);
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            if(result == 0){
            	throw new DaoException("Administrateur à modifier inconnu.");
            }else{
        		if(validate){
        			word1 = "plaisir ";
        			word2 = "activé.\n\n";
        			subject = "Activation de compte administrateur";
        		}else{
        			word1 = "regret ";
        			word2 = "désactivé.\n\n";
        			subject = "Désactivation de compte administrateur";
        		}
        		message = "Bonjour Madame, Monsieur,\n\nNous avons le ";
        		message += word1;
        		message += "de vous annoncer que votre compte administrateur ";
        		message += "ayant pour identifiant '" + email +"' vient d'avoir été ";
        		message += word2;
        		message += "Les administrateurs du site d'évaluation des stagiaires";
            	this.sendAnEmail(subject, message, email);
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
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String message = null;
        String surname = trainee.getSurname();
        String name = trainee.getName();
        String phone = trainee.getPhone();
        String company = trainee.getCompany();
        String email = trainee.getEmail();
        String password = trainee.getPassword();
        if(!Pattern.matches("^[a-zA-Z]+$", surname) || !Pattern.matches("^[a-zA-Z]+$", name) || !Pattern.matches("^[0-9]{10}$", phone) || !Pattern.matches("^.+$", company) || !Pattern.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$", email) ||  !Pattern.matches("^.{6,}$", password)){
        	throw new DaoException("Veuillez saisir des données cohérentes.");
        }  
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement("INSERT INTO Trainee (email, surname, name, password, phone, company, accountCreation, accountStatus) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?);");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, surname.toLowerCase());
            preparedStatement.setString(3, name.toLowerCase());
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, company);
            preparedStatement.setBoolean(7, trainee.isActive());
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            if(result == 0){
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
            	this.sendAnEmail("Création de compte stagiaire", message, trainee.getEmail());
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
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String message = null;
        String surname = superUser.getSurname();
        String name = superUser.getName();
        String phone = superUser.getPhone();
        String company = superUser.getCompany();
        String email = superUser.getEmail();
        String password = superUser.getPassword();
        if(!Pattern.matches("^[a-zA-Z]+$", surname) || !Pattern.matches("^[a-zA-Z]+$", name) || !Pattern.matches("^[0-9]{10}$", phone) || !Pattern.matches("^.+$", company) || !Pattern.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$", email) ||  !Pattern.matches("^.{6,}$", password)){
        	throw new DaoException("Veuillez saisir des données cohérentes.");
        }  
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement("INSERT INTO SuperUser (email, surname, name, password, phone, company, accountCreation, accountStatus) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?);");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, surname.toLowerCase());
            preparedStatement.setString(3, name.toLowerCase());
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, company);
            preparedStatement.setBoolean(7, superUser.isActive());
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            if(result == 0){
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
            	this.sendAnEmail("Création de compte administrateur", message, superUser.getEmail());
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
    
    private void sendAnEmail(String subject, String message, String email) throws DaoException{
		Email simpleEmail = new SimpleEmail();
		simpleEmail.setHostName(HOST_NAME);
		simpleEmail.setSmtpPort(SMTP_PORT);    	
		simpleEmail.setAuthentication(this.emailAccount.getEmail(), this.emailAccount.getPassword());
		simpleEmail.setDebug(true);
		simpleEmail.setSSLOnConnect(true);
		simpleEmail.setStartTLSEnabled(true);
		try {
			simpleEmail.setFrom(this.emailAccount.getEmail());
			simpleEmail.setSubject(subject);
			simpleEmail.setMsg(message);
			simpleEmail.addTo(email);
			simpleEmail.send();
		} catch (EmailException e) {
        	throw new DaoException("Impossible d'envoyer le mail : " + e.getMessage());
		}
    }
}