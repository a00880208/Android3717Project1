PetBits
by Alex Lam, Eric Tsang, Sebastian Pelka
=====================================================================================
Project Intent
The three of us had decided to put some time during our school year and put together an application to challenge our android programming skills.
We had allocated 4 months of time to work on this while attending BCIT and this is what we have put together.

The original intent of PetBits was to create an app where users can discover other pet lovers’ adorable pet images. 
To facilitate this, we envisioned and designed an app where users could:
  login with google+
  upload images of their pets
  select tags for their images to aid in other users’ searches
  use a search feature to find other people’s pets
  manage their own personal image gallery
  
What We Accomplished
Our original phase of work included diagramming, consisting of sketches on the whiteboard and in pencil, which helped us keep on track with development. We were able to successfully implement the core functionality of our project, which includes:
  Acount creation with MongoDB, storing user names and passwords in a collection
  Image uploading using the imgur API and storage of image URLs in a MongoDB collection
  Using the Volley API to queue network requests to MongoDB
  Implementing a search feature to find images uploaded by a specific user
  Enabled a user to add and remove images associated with their account
  Implemented a yellow user interface

What We Didn’t Accomplish
During the time we have put aside to work on this project, we have de-scoped our project because some of our original ideas were overly ambitious and we felt that we could not present a completed app within the given time if we had kept these features in:
  1) No moderation of content
    usernames are not checked for inappropriate content.
    Images are not checked for spamming, or for sexual, explicit, or unrelated content.
    A solution to these problems would be to implement a user-driven reporting system, which could monitor accounts which have been reported for inappropriate names, image spamming, or inappropriate use of the software.
  2) No security
    A solution to this would be to hash user info; even if it is passed in plaintext, it is unrecognizable to someone reading the http request text.
  3) No google + login
    This idea was replaced by user information storage in MongoDB. We had no plans to continue with the Google + idea, because we decided we could learn more by making a login system from scratch.
  4) No Like or upvote for pictures
    This was something we really wanted to keep in, but dropped late in the development cycle due to time constraints. This would consist of a “thumbs up” or “heart” icon which the user could click if they liked the image they saw.
