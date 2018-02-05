# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed
- ApplicationController to a sigleton (ApplicationScoped), with eager behavior.

## 1.0.0

### Added
- Project initialized with Hibernate + JPA, JSF and PrimeFaces.
- JUnit for unit testing.
- PrettyFaces for path rewrites.
- QRGen for QRCode manipulation.
- Code entity that represents an unsecure QRCode link. 
- SecureCode entity that represents a password secure QRCode link.
- DAO Interface and DatabaseHandler class that holds database access methods.
- CodeDAO that holds the database transactions for the Code Entity.
- SecureCodeDAO that holds the database transactions for the SecureCode Entity.
- ResourceService a facade for resource DAOs.
- CodeController that's responsible for the code resource manipulation.
- UnlockController that's responsible for the password checking and code URI redirection.
- ResultController that's responsible for the QRCode generation.
- Layout template that's the skeleton of the views.
- Index view: Main page of the application, where the user inputs the URI to encode.
- Mail view: Same as the Index view, but as a aux for "mailto" uri generation.
- Result view: Where the QRCode is shown.
- Unlock view: Where the user will type the SecureCode password to unlock the redirect.
- 404 and 500 error handlers.

[Unreleased]: https://github.com/JayBIOS/quickresponse/compare/v1.0.0...HEAD