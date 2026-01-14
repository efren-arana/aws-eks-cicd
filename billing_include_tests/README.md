# Spring boot CRUD By [Efrén Arana]

**Content**   
1. [How contribute to this project](#how-contribute-to-this-project)
2. [Getting Started](#getting-started)
## How contribute to this project. 

1. First, you need to fork the repository that you want to contribute to. This will create a copy of the repository under your own GitHub account. You can fork a repository by clicking the Fork button on the top right corner of the repository page.

2. Next, you need to clone your forked repository to your local machine. This will allow you to work on the project files offline. You can clone your forked repository by using the git clone command with the URL of your fork. 

3. After cloning your forked repository, you need to create a branch to work on your changes. A branch is a separate version of the code that you can modify without affecting the main branch. You can create a branch by using the git branch command with the name of your branch. 

4. Once you have created a branch, you need to switch to it by using the git checkout command with the name of your branch. This will make your branch the active one and any changes you make will be applied to it.

5. Now you can start working on your changes. You can edit, add, or delete any files in your branch as you wish. You can use any code editor or IDE that you prefer. 

6. After making your changes, you need to commit them to your branch. A commit is a snapshot of your changes that records what you have done and why. You can commit your changes by using the git commit command with a message that describes your changes. 

7. Next, you need to push your changes to your forked repository on GitHub. This will upload your branch and its commits to your online repository. You can push your changes by using the git push command with the name of your remote (usually origin) and the name of your branch. 

8. Finally, you need to create a pull request from your branch to the original repository that you forked from. A pull request is a request for the maintainers of the original repository to review and merge your changes into their main branch. You can create a pull request by clicking the Pull Request button on your forked repository page on GitHub.

## Getting Started

### Requirements
1. Java 17 or above

### Installation
1. Clone the repository.
2. Buidl the project, using a profiles local or prod
for prod use:
```sh
    ./mvnw package -Pprod.
```
for local use:
```sh
    ./mvnw package -Plocal.
```
3. Run the command java -jar target/*.jar.
4. Open your browser and go to url: http://localhost:7080/swagger-ui/index.html
choose the correct port that you see on log at startup process

