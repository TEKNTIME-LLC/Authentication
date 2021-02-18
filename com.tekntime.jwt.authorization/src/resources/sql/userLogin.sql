USE [master]
GO

/****** Object:  Table [dbo].[userLogin]    Script Date: 11/23/2020 10:13:15 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[userLogin](
	[id] [int] NOT NULL,
	[loginName] [varchar](50) NULL,
	[firstName] [text] NULL,
	[lastName] [text] NULL,
	[middleInitial] [text] NULL,
	[password] [varchar](max) NULL,
	[createDate] [datetime] NULL,
	[updateDate] [datetime] NULL,
	[expiryDate] [datetime] NULL,
	[isActive] [bit] NULL,
	[isLocked] [bit] NULL,
	[lastLoginDate] [datetime] NULL,
	[loginAttempt] [int] NULL,
	[isDeleted] [bit] NULL,
	[email] [varchar](50) NULL,
	[phone] [numeric](18, 0) NULL,
	[addressLine1] [varchar](50) NULL,
	[addressLine2] [varchar](50) NULL,
	[city] [text] NULL,
	[state] [text] NULL,
	[zipCode] [numeric](18, 0) NULL,
	[isEmailNotification] [bit] NULL,
	[isTextMessageNotification] [bit] NULL,
	[hashType] [varchar](25) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO


