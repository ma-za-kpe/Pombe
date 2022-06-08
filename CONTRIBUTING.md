# Contributing
Thank you for taking the time to contribute to Pombe!

To contribute to the Pombe repository, you may find an existing issue or create a new issue. Afterwards, use the [Contribution Guidelines](#contribution-guidelines) to help get started on your contribution.

## Finding an Issue

Issue labels are used to track various tasks related to the development of Pombe. [Search issues here](/labels) to find issues that match your interests, familiarity with the software, and skills.

  - `good first issue`: Issues that are open to new contributors and beginner-friendly
  - `help wanted`: Issues that are open to contributors and not necessarily just for beginners

You may also work on issues with other labels not listed here.

## Creating a New Issue

Before creating a new issue, please search the Pombe repository to find similar issues that may have already been posted.

If you want to work on something that has no existing issue, follow these steps:
  1. Create a new issue to propose your changes. Clearly explain the problem, the steps you took to replicate that problem, and your solution.
  2. Wait for your issue to be reviewed and approved for accepting pull requests. Answer questions as needed.
  3. If your issue has been approved, you may assign the issue to yourself and work on code. 

## Contribution Process

If you have found an issue to work on, follow these steps:
  1. If you are not assigned the issue, comment on the issue to request to be assigned. This avoids potential conflicts with others. 
  2. Follow the [Contribution Guidelines](#contribution-guidelines), write your code, and submit your Pull Request to the **develop** branch.

## Contribution Guidelines

### Submit Pull Requests to the `develop` branch
- There are 3 major branches in the Pombe repository:
  - **master** 
  - **beta**: for QA testing
  - **develop**: for open contributions and development

## Getting Started on your Contribution

  1. Fork and clone the repository from the **master** branch.
  2. Make changes to the **develop** branch of your fork. Do not make changes to the **master** branch of your fork. 
  3. Retrieve your API Key from the [CocktailDB API](https://www.thecocktaildb.com/api.php).
  4. In your repository, create a `local.properties` file. 
  5. Add `apiKey = <YOUR_API_KEY>` in the `local.properties` file. `<YOUR_API_KEY>` will be the API key you received from the [CocktailDB API](https://www.thecocktaildb.com/api.php). Build and run the app.
      - You will need [Android Studio BumbleBee](https://developer.android.com/studio) to run the app.
  6. Run your code through the following tests in your terminal:
      - gradlew test
      - gradelw connectedAndroidTest
  7. After you have finalized your code and it passes both tests, create your Pull Request to be added to the **develop** branch.

## Creating Your Pull Request

1. Submit your Pull Request to the **develop** branch.
2. Link your Pull Request to a related issue.
3. Wait for code review and answer any questions as soon as you can.
