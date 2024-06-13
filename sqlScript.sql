CREATE DATABASE MOVIES
GO
USE MOVIES
GO

CREATE TABLE Roles(
	IDRole INT PRIMARY KEY IDENTITY,
	RoleType NVARCHAR(25) not null
)

CREATE TABLE Users(
	IDUser INT PRIMARY KEY IDENTITY,
	Username NVARCHAR(25) not null,
	HashedPassword NVARCHAR(600) not null,
	RoleID INT FOREIGN KEY REFERENCES Roles(IDRole)
)

CREATE TABLE Movie(
	IDMovie INT PRIMARY KEY IDENTITY,
	Title NVARCHAR(100) not null,
	Published NVARCHAR(25) not null,
	[Description] NVARCHAR(MAX) not null,
	OriginalTitle NVARCHAR(100) not null,	
	Duration NVARCHAR(10),
	PosterPath NVARCHAR(300) not null,
	Link NVARCHAR(300) not null,
	Expected NVARCHAR(25) not null
)

CREATE TABLE Actor(
	IDActor INT PRIMARY KEY IDENTITY,
	ActorName NVARCHAR(50)
)

CREATE TABLE Director(
	IDDirector INT PRIMARY KEY IDENTITY,
	DirectorName NVARCHAR(50) null
)

CREATE TABLE Genre(
	IDGenre INT PRIMARY KEY IDENTITY,
	GenreName NVARCHAR(50)
)

CREATE TABLE MovieActor
(
	IDMovieActor INT not null PRIMARY KEY IDENTITY,
	MovieID INT not null FOREIGN KEY REFERENCES Movie(IDMovie),
	ActorID INT null FOREIGN KEY REFERENCES Actor(IDActor)
)

CREATE TABLE MovieDirector
(
	IDMovieDirector INT not null PRIMARY KEY IDENTITY,
	MovieID INT not null FOREIGN KEY REFERENCES Movie(IDMovie),
	DirectorID INT null FOREIGN KEY REFERENCES Director(IDDirector)
)

CREATE TABLE MovieGenre
(
	IDMovieGenre INT not null PRIMARY KEY IDENTITY,
	MovieID INT not null FOREIGN KEY REFERENCES Movie(IDMovie),
	GenreID INT null FOREIGN KEY REFERENCES Genre(IDGenre)
)


INSERT INTO Roles VALUES ('Admin')
INSERT INTO Roles VALUES ('User')

--username: admin password: admin
--username: admin2 password: admin2
INSERT INTO Users VALUES ('admin', '$2a$12$B712dWcDsV05MM3zCsbSPOlQlqwuWLFrdH//yhiBz8PtWCli5fqXS', 1)
INSERT INTO Users VALUES ('admin2', '$2a$12$4NtyfIUSMZlVm3yidvJuOOGQIG26KOU78KwnvBdn5hZrJeTjTmU/S', 1)
GO

--user
CREATE PROC createUser
	@Username NVARCHAR(25),
	@HashedPassword NVARCHAR(600),
	@RoleID INT,
	@IdUser INT OUTPUT
AS
BEGIN
	INSERT INTO Users VALUES(@Username, @HashedPassword, @RoleID)
	SET @IdUser = SCOPE_IDENTITY()
END
GO

CREATE PROC selectUser
	@Username NVARCHAR(25)
AS
BEGIN 
	SELECT * FROM Users WHERE Username = @Username
END
GO

CREATE PROC userExists
	@Username NVARCHAR(25),
	@exists INT OUTPUT
AS
BEGIN
	DECLARE @count INT
	SELECT @count = COUNT(*) FROM Users WHERE Username = @Username
	IF @count = 1
		BEGIN
			SET @exists = 1
		END
	ELSE
		BEGIN
			SET @exists = 0
		END
END
GO

--actors
CREATE PROC selectActor
	@IDActor INT
AS
BEGIN
	SELECT * FROM Actor WHERE IDActor = @IDActor
END
GO

CREATE PROC selectActorName
	@ActorName NVARCHAR(50)
AS
BEGIN
	SELECT * FROM Actor WHERE ActorName = @ActorName
END
GO

--rss
CREATE PROC createActor
	@MovieID INT,
	@ActorName NVARCHAR(50),
	@IdActor INT OUTPUT
AS
BEGIN
	INSERT INTO Actor VALUES (@ActorName)
	SET @IdActor = SCOPE_IDENTITY()
	INSERT INTO MovieActor VALUES (@MovieID, @IdActor)
END
GO

CREATE PROC selectAllActors
AS
BEGIN
	SELECT * FROM Actor
END
GO

CREATE PROC createNewActor
	@ActorName NVARCHAR(50)
AS
BEGIN
	INSERT INTO Actor VALUES (@ActorName) 
END
GO

CREATE PROC selectActors
	@IDMovie INT
AS
BEGIN
	SELECT a.IDActor, a.ActorName FROM Actor a
	join MovieActor mc ON mc.ActorID=a.IDActor
	join Movie m ON m.IDMovie=mc.MovieID
	WHERE IDMovie = @IDMovie
END
GO

CREATE PROC updateActor
	@IdActor INT,
	@ActorName NVARCHAR(50)
AS
BEGIN
	UPDATE Actor SET 
	ActorName = @ActorName
	WHERE IdActor = @IdActor
END
GO

CREATE PROC actorMovieRelation
	@IdActor INT,
	@returnCode INT output
AS
BEGIN
	DECLARE @count INT
	SELECT @count = COUNT(ActorID) FROM MovieActor WHERE ActorID = @IdActor
	IF @count = 0
		BEGIN
			SET @returnCode = 0
		END
	ELSE
		BEGIN
			SET @returnCode = -1
		END
END
GO

CREATE PROC deleteActor
	@IdActor INT
AS
BEGIN
	DELETE FROM Actor WHERE IdActor = @IdActor
END
GO


--director
CREATE PROC selectDirectorId
	@IDDirector INT
AS
BEGIN
	SELECT * FROM Director WHERE IDDirector = @IDDirector
END
GO

CREATE PROC updateDirector
	@IdDirector INT,
	@DirectorName NVARCHAR(50)
AS
BEGIN
	UPDATE Director SET 
	DirectorName = @DirectorName
	WHERE IdDirector = @IdDirector
END
GO

CREATE PROC directorMovieRelation
	@IdDirector INT,
	@returnCode INT output
AS
BEGIN
	DECLARE @count INT
	SELECT @count = COUNT(DirectorID) FROM MovieDirector WHERE DirectorID = @IdDirector
	IF @count = 0
		BEGIN
			SET @returnCode = 0
		END
	ELSE
		BEGIN
			SET @returnCode = -1
		END
END
GO

CREATE PROC deleteDirector
	@IdDirector INT
AS
BEGIN
	DELETE FROM Director WHERE IdDirector = @IdDirector
END
GO

CREATE PROC selectDirector
	@DirectorName NVARCHAR(50)
AS
BEGIN
	SELECT * FROM Director WHERE DirectorName = @DirectorName
END
GO

CREATE PROC createDirector
	@MovieID INT,
	@DirectorName NVARCHAR(50),
	@IdDirector INT OUTPUT
AS
BEGIN 
	INSERT INTO Director VALUES (@DirectorName)
	SET @IdDirector = SCOPE_IDENTITY()
	INSERT INTO MovieDirector VALUES (@MovieID, @IdDirector)
END
GO

CREATE PROC selectAllDirectors
AS
BEGIN
	SELECT * FROM Director
END
GO

CREATE PROC createNewDirector
	@DirectorName NVARCHAR(50)
AS
BEGIN
	INSERT INTO Director VALUES (@DirectorName) 
END
GO

CREATE PROC selectDirectors
	@IDMovie INT
AS
BEGIN
	SELECT IDDirector, DirectorName FROM Director 
	join MovieDirector ON DirectorID=IDDirector
	join Movie ON IDMovie=MovieID
	WHERE IDMovie = @IDMovie
END
GO


--genre
CREATE PROC selectGenreId
	@IDGenre INT
AS
BEGIN
	SELECT * FROM Genre WHERE IDGenre = @IDGenre
END
GO

CREATE PROC selectGenre
	@GenreName NVARCHAR(50)
AS
BEGIN
	SELECT * FROM Genre WHERE GenreName = @GenreName
END
GO

--rss
CREATE PROC createGenre
	@MovieID INT,
	@GenreName NVARCHAR(50),
	@IdGenre INT OUTPUT
AS
BEGIN 
	INSERT INTO Genre VALUES (@GenreName)
	SET @IdGenre = SCOPE_IDENTITY()
	INSERT INTO MovieGenre VALUES (@MovieID, @IdGenre)
END
GO

CREATE PROC selectAllGenres
AS
BEGIN
	SELECT * FROM Genre
END
GO

CREATE PROC selectGenres
	@IDMovie INT
AS
BEGIN
	SELECT IDGenre, GenreName FROM Genre 
	join MovieGenre ON GenreID=IDGenre
	join Movie ON IDMovie=MovieID
	WHERE IDMovie = @IDMovie
END
GO


--movies
CREATE PROC createMovie
	@Title NVARCHAR(100),
	@Published NVARCHAR(25),
	@Description NVARCHAR(MAX),
	@OriginalTitle NVARCHAR(100),	
	@Duration NVARCHAR(10),
	@PosterPath NVARCHAR(300),
	@Link NVARCHAR(300),
	@Expected NVARCHAR(25),
	@IdMovie INT OUTPUT
AS
BEGIN 
	INSERT INTO Movie VALUES(@Title, @Published, @Description, @OriginalTitle, @Duration, @PosterPath, @Link, @Expected)
	SET @IdMovie = SCOPE_IDENTITY()
END
GO

CREATE PROC setMovieDirectors
	@MovieID INT,
	@IDDirector INT
AS
BEGIN
	INSERT INTO MovieDirector VALUES (@MovieID, @IdDirector) 
	SELECT * FROM MovieDirector
	WHERE MovieID = @MovieID
END
GO

CREATE PROC setMovieActors
	@MovieID INT,
	@IDActor INT
AS
BEGIN
	INSERT INTO MovieActor VALUES (@MovieID, @IDActor) 
	SELECT * FROM MovieActor
	WHERE MovieID = @MovieID
END
GO

CREATE PROC setMovieGenres
	@MovieID INT,
	@IDGenre INT
AS
BEGIN
	INSERT INTO MovieGenre VALUES (@MovieID, @IDGenre) 
	SELECT * FROM MovieGenre
	WHERE MovieID = @MovieID
END
GO

CREATE PROC selectMovies
AS
BEGIN
	SELECT * FROM Movie
END
GO

CREATE PROC selectMovie
	@IDMovie INT
AS
BEGIN
	SELECT * FROM Movie WHERE IDMovie = @IDMovie
END
GO

CREATE PROC updateMovie
	@Title NVARCHAR(100),
	@Published NVARCHAR(25),
	@Description NVARCHAR(MAX),
	@OriginalTitle NVARCHAR(100),	
	@Duration NVARCHAR(10),
	@PosterPath NVARCHAR(300),
	@Link NVARCHAR(300),
	@Expected NVARCHAR(25),
	@IDMovie INT
AS
BEGIN
	UPDATE Movie SET 
	Title = @title, Published = @Published, [Description] = @Description, 
	OriginalTitle = @OriginalTitle, Duration = @Duration, 
	PosterPath = @PosterPath, Link = @Link, Expected = @Expected
	WHERE IDMovie = @IDMovie
END
GO

CREATE PROC removeMovieActors
	@MovieID INT
AS
BEGIN
	DELETE FROM MovieActor
	WHERE MovieID = @MovieID
END
GO

CREATE PROC removeMovieDirectors
	@MovieID INT
AS
BEGIN
	DELETE FROM MovieDirector
	WHERE MovieID = @MovieID
END
GO

CREATE PROC removeMovieGenres
	@MovieID INT
AS
BEGIN
	DELETE FROM MovieGenre
	WHERE MovieID = @MovieID
END
GO

CREATE PROC deleteMovie
	@MovieID INT
AS
BEGIN
	DELETE FROM MovieGenre WHERE MovieID = @MovieID
	DELETE FROM MovieActor WHERE MovieID = @MovieID
	DELETE FROM MovieDirector WHERE MovieID = @MovieID
	DELETE FROM Movie WHERE IDMovie = @MovieID
END
GO

--deletion script
CREATE PROC deleteAllMoviesFromDatabase
AS
BEGIN
	DELETE FROM MovieActor
	DELETE FROM MovieDirector
	DELETE FROM MovieGenre
	DELETE FROM Actor
	DELETE FROM Director
	DELETE FROM Genre
	DELETE FROM Movie
END
GO






