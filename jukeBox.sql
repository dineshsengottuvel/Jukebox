create database jukeBox;
use jukeBox;

create table Songs(
SongId BIGINT auto_Increment primary key,
SongName varchar(100) not null,
Genre varchar(10) not null,
AlbumName varchar(20),
ArtistName varchar(30) not null,
ArtistId bigint,
duration time(2) not null,
Rating float,
PublishedYear year,
Language varchar(10),
url varchar(300),constraint ck_r check (Rating <=10),
constraint fka foreign key (ArtistId) references Artist(ArtistId))auto_increment=10000000;

create table Podcast(
PodcastId BIGINT auto_Increment primary key,
PodcastName varchar(100) not null,
Genre varchar(10) not null,
Published_Date date,
ArtistName varchar(30) ,
ArtistId bigint,
Total_duration time(2) not null,
NoOfEpisodes int not null,
Rating int,
Language varchar(10),
url varchar(100),constraint ck_r1 check (Rating <=10),
constraint fk_a foreign key (ArtistId) references Artist(ArtistId))auto_increment=50000000;

create view SongView as select 
SongName,Genre,   AlbumName,     ArtistName,duration,     Rating,      PublishedYear,Language,SongId,url from Songs ;

create view PodcastView as select 
PodcastName,Genre, NoOfEpisodes ,ArtistName,Total_duration,Rating,      Published_Date, Language,PodcastId,url from podcast;

create table Artist(
ArtistId BIGINT auto_Increment primary key,
ArtistName varchar(30) not null,
Last_Update Date,
NoOfSongs int default null,
location varchar(20),
NoOfLanguages int,
language1 varchar(10),
language2 varchar(10) default null,
language3 varchar(10) default null);

create table User(
UserId BIGINT auto_Increment primary key,
User_Name varchar(20) not null,
MobileNo BIGINT,
EmailId varchar(20),
Password varchar(20))auto_increment=80000000;

create table PlayList(
PlayListId BIGINT auto_increment primary key,
PlayList_Name varchar(20) not null,
SongId BIGINT default '0',
PodcastId BIGINT default '0',
UserId BIGINT,
MediaType varchar(20),
Position int not null,
constraint ck_k check (MediaType in('Song','Podcast')),
constraint fk_l foreign key (SongId) references Songs(SongId),
constraint fk_m foreign key (PodcastId) references Podcast(PodcastId),
constraint fk_o foreign key (UserId) references User(UserId))auto_increment=90000000;

select * from Podcast;
select * from Artist;
select * from Songs;
select * from User;
select * from PlayList;

drop table Artist;
drop table Podcast;
drop table Songs;
drop table User;
drop table PlayList;

insert into PlayList(PlayList_Name ,SongId,PodcastId,UserId,MediaType,Position) values 
('myplayA',10000000 , null     ,80000001,'Song',1),
('myplayA',10000001 , null     ,80000001,'Song',2),
('myplayA',null     , 50000000 ,80000001,'podcast',3),
('myplayB',10000003 , null     ,80000002,'Song',1 ),
('myplayB',10000004 , null     ,80000002,'Song',2 ),
('myplayB',10000005 , null     ,80000002,'Song',3 ),
('myplayC',10000004 , null     ,80000003,'Song',1 ),
('myplayC',10000003 , null     ,80000003,'Song',2 );



insert into Songs(SongName,Genre,AlbumName,ArtistName,ArtistId,duration,Rating,PublishedYear,language, url) values 
('Naanga vera mari',      'Jazz',    'Valimai',          'Yuvan',         5,'00:4:41',8,2022,'Tamil','C:\Users\Dinesh\eclipse-workspace\JDBC\SongFiles\01.wav'),
('Pyar Kiya To Darna Kya','Classic', 'Mughal-E-Azam',    'A R Rahman',    2,'00:2:31',9,1960,'Hindi', 'C:\Users\Dinesh\eclipse-workspace\JDBC\SongFiles\2.wav'),
('Pinga',                 'Hip hop', 'Bajirao Mastani',  'A R Rahman',    5,'00:5:20',8,2015,'Hindi', 'C:\Users\Dinesh\eclipse-workspace\JDBC\SongFiles\3.wav'),
('Rowdy Baby',            'Disco',   'Maari',            'Anirudh',       1,'00:2:22',7,2018,'Tamil', 'C:\Users\Dinesh\eclipse-workspace\JDBC\SongFiles\4.wav'),
('Sri valli',             'Melody',  'Pushpa',           'Sid Sriram',    3,'00:6:35',9,2022,'Telegu', 'C:\Users\Dinesh\eclipse-workspace\JDBC\SongFiles\5.wav'),
('Oh penne penne',        'Blues',   'Vanakkam Chennai', 'Anirudh',       1,'00:2:21',10,2022,'Tamil', 'C:\Users\Dinesh\eclipse-workspace\JDBC\SongFiles\6.wav'),
('New yory nagaram',      'Vocal',   'Sillunu Oru Kadhal','Shreya Goshal',4,'00:5:52',8,2011,'Kannada' ,'C:\Users\Dinesh\eclipse-workspace\JDBC\SongFiles\8.wav'),
('Maguva Maguva',         'Folk',    'Vakeel Saab',      'Sid Sriram',    3,'00:1:01',6,2022,'Hindi',  'C:\Users\Dinesh\eclipse-workspace\JDBC\SongFiles\9.wav'),
('Ennodu Nee Irundhaal',  'Soul',    'I',                'A R rahman',    2,'00:2:28',8,2019,'Tamil' , 'C:\Users\Dinesh\eclipse-workspace\JDBC\SongFiles\10.wav'),
('Teri Meri Prem Kahani', 'Jazz',    'Bodygaurd',        'Shreya Goshal', 4,'00:5:41',9,2010,'Hindi', 'C:\Users\Dinesh\eclipse-workspace\JDBC\SongFiles\11.wav');

update songs set url="C:\\Users\\Dinesh\\eclipse-workspace\\JDBC\\SongFiles\\01.wav"  where songid=10000000;
update songs set url='C:\\Users\\Dinesh\\eclipse-workspace\\JDBC\\SongFiles\\2.wav'  where songid=10000001;
update songs set url="C:\\Users\\Dinesh\\eclipse-workspace\\JDBC\\SongFiles\\3.wav"  where songid=10000002;
update songs set url="C:\\Users\\Dinesh\\eclipse-workspace\\JDBC\\SongFiles\\4.wav"  where songid=10000003;
update songs set url="C:\\Users\\Dinesh\\eclipse-workspace\\JDBC\\SongFiles\\5.wav"  where songid=10000004;
update songs set url="C:\\Users\\Dinesh\\eclipse-workspace\\JDBC\\SongFiles\\6.wav"  where songid=10000005;
update songs set url="C:\\Users\\Dinesh\\eclipse-workspace\\JDBC\\SongFiles\\8.wav"  where songid=10000006;
update songs set url="C:\\Users\\Dinesh\\eclipse-workspace\\JDBC\\SongFiles\\9.wav"  where songid=10000007;
update songs set url="C:\\Users\\Dinesh\\eclipse-workspace\\JDBC\\SongFiles\\10.wav"  where songid=10000008;
update songs set url="C:\\Users\\Dinesh\\eclipse-workspace\\JDBC\\SongFiles\\11.wav"  where songid=10000009;

insert into Podcast(PodcastName,Genre,Published_Date,ArtistName,ArtistId,Total_duration,NoOfEpisodes,Rating, Language, url) values 
('Bhaskar Bose',          'Thriller',   '2022-01-23',  'Asif Syed',   6      ,'02:01:01' ,9,  8,   'Hindi', 'C:\Users\Dinesh\eclipse-workspace\JDBC\SongFiles\12.wav'),
('Nallana Murukku',       'Comedy',     '2021-02-07',  'R J Balaji',   7     ,'00:31:51' ,8,  9,   'Tamil', 'C:\Users\Dinesh\eclipse-workspace\JDBC\SongFiles\13.wav'),
('The Tollywood Project', 'Chilling',   '2022-01-23',  'Nambi',        8 ,'02:01:01' ,10, 6,   'Telegu', 'C:\Users\Dinesh\eclipse-workspace\JDBC\SongFiles\14.wav'),
('Indian Noir',           'Thriller',   '2022-01-23',  'Nikesh Murali', 9    ,'02:01:01' ,5,  10,  'Hindi', 'C:\Users\Dinesh\eclipse-workspace\JDBC\SongFiles\15.wav'),
('The desi crime',        'Crime',      '2022-01-23',  'Deva',          10    ,'02:01:01' ,6,  9,   'Hindi', 'C:\Users\Dinesh\eclipse-workspace\JDBC\SongFiles\16.wav');

update podcast set url="C:\\Users\\Dinesh\\eclipse-workspace\\JDBC\\SongFiles\\12.wav"  where podcastid=50000000;
update podcast set url="C:\\Users\\Dinesh\\eclipse-workspace\\JDBC\\SongFiles\\13.wav"  where podcastid=50000001;
update podcast set url="C:\\Users\\Dinesh\\eclipse-workspace\\JDBC\\SongFiles\\14.wav"  where podcastid=50000002;
update podcast set url="C:\\Users\\Dinesh\\eclipse-workspace\\JDBC\\SongFiles\\15.wav"  where podcastid=50000003;
update podcast set url="C:\\Users\\Dinesh\\eclipse-workspace\\JDBC\\SongFiles\\16.wav"  where podcastid=50000004;

insert into Artist(ArtistName,Last_Update,NoOfSongs,location,NoOfLanguages,language1,language2,language3) values 
('Anirudh','2021-02-19',1006,'Chennai',2,'Tamil','Telegu',default),
('A R rahman','2021-03-02','9045','Mumbai',3,'Hindi','Tamil','Telegu'),
('Sid Sriram','2021-02-01',1006,'Banglore',2,'Hindi','Tamil',default),
('Shreya Goshal','2020-10-12',204,'Kolkata',2,'Hindi','Kannada',default),
('Yuvan','2022-02-12',        823,'Chennai',1,'Tamil',default,default),
('Asif Syed','2019-01-28'    ,default,'Mumbai',1,'Hindi',default,default),
('RJ Balaji','2020-10-09'    ,default,'Chennai',1,'Tamil',default,default),
('Nambi','2022-01-08'        ,default,'Visakapatnam',1,'Telegu',default,default),
('Nikesh Murali','2018-05-21',default,'Kolkata',1,'Hindi',default,default),
('Deva','2011-07-09'         ,default,'Mumbai',1,'Hindi',default,default);

insert into User(User_Name,MobileNo,EmailId,password) values 
('ravi',   9000000000,'ravi@gmail.com','ravi'),
('Antony', 9000001230,'antony@gmail.com','antony'),
('Nandhu', 9482000000,'nandhu@gmail.com','nandhu'),
('Punith', 9073600000,'punith@gmail.com','punith'),
('Gowtham',9091500000,'gowtham@gmail.com','gowtham');
