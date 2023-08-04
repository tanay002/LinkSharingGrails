package linksharing

import linksharing.util.RoleType

class BootStrap {

    def init = { servletContext ->

        new User(firstName: "Tanay",lastName: "Saxena",emails: "tanay@yopmail.com",
                username:"Tanay002",mobNo:"8878056735",password:"Tanay1",
        isActive: true,photo: null,dateCreated: new Date(),lastUpdated: new Date(),
                roles: Arrays.asList(new Roles(roleType: RoleType.ADMIN))).save();

        new User(firstName: "Deepak",lastName: "Verma",emails: "deepak@yopmail.com",
                username:"Deepak001",mobNo:"9098987876",password:"Deep1",
                isActive: true,photo: null,dateCreated: new Date(),lastUpdated: new Date(),
                roles: Arrays.asList(new Roles(roleType: RoleType.NORMAL_USER))).save();

         println "initializing data..."

    }
    def destroy = {
    }
}
