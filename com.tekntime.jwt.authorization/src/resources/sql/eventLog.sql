USE [master]
GO

/****** Object:  Table [dbo].[userLogin]    Script Date: 1/19/2021 9:20:52 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[eventLog](
	[id] [int] NOT NULL,
	[date] [datetime] NULL,
	[time] [datetime] NULL,
	[event] [text] NULL,
	[classname] [text] NULL,
	[thread] [varchar](max) NULL,
	[message] [varchar](max) NULL,
	[errormsg] [varchar](max) NULL,
	[stacktrace] [varchar] (max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

-------------------------------
MYSQL

CREATE TABLE eventLog(
	id int NOT NULL AUTO_INCREMENT,
	date datetime NULL,
	time datetime NULL,
	event text NULL,
	classname varchar(2000) NULL,
	thread varchar(2000) NULL,
	message varchar(2000) NULL,
	errormsg varchar(2000) NULL,
	stacktrace varchar(2000) NULL,
    PRIMARY KEY ( id )
 )


