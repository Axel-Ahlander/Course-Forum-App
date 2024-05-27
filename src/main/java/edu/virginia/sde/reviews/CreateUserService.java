package edu.virginia.sde.reviews;

public class CreateUserService {
    private User user;
    public CreateUserService( User user){
        this.user = user;
    }

    public void saveUser(){
        UserDAO DAO = new UserDAO();
        DAO.save(this.user);
    }

}
