# Jira Cycle Time Tool

[![Build Status](https://travis-ci.org/PhonyLou/jira-calc.svg?branch=master)](https://travis-ci.org/PhonyLou/jira-calc)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

## What's this?
This is a tool to generate Jira Cycle Time report


## How to use this tool?

### Generate package

Run command ```mvn clean install```, the jar file will be generated and copied to /release folder.

### Get Token

1. Generate ```jira API token``` at [here](https://id.atlassian.com/manage/api-tokens).
2. Get Jira Token. Once getting ```Jira API token```, generate jira-token by executing
    ```
    curl -v https://arlive.atlassian.net/ --user [Your-Email]:[Jira-API-Token]
    ```
    The Jira Token looks like ```Basic cWlsaW...```

    Then copy the token into ```release/env/token.txt``` with the format like:
    ```
    jira-token: [Paste-Your-Token-Here]
    ```

### Config JQL

Update ```release/env/jql.txt``` file to satisfy your team.

### Config Stages(Make sure use the correct stage, you can debug from network in jira to get the exactly value)

Update ```release/env/stages.txt``` file to satisfy your team.

### Get report:
```
cd ./release

## Start app
sh 1-startApp.sh

## Get report
sh 2-getJiraReport.sh

## Stop app
3-stopApp.sh
```

Then you can get the report which named **currentTimestamp**.csv

# Advance
## Precondition
Please make sure you have installed JVM before starting the application.

## How to change the result scope?
Open ```release/env/jql.txt``` and change the filter condition

#### JQL Example:
```
jql: project = Ao-li-gei AND (assignee in (Aotian.Long, Liangchen.ye, TieChui.Zhuge) OR assignee was in (Aotian.Long, Liangchen.ye, TieChui.Zhuge)) AND status changed to done before now() after -30d  ORDER BY priority DESC
```

#### Stages:
```
card-stage: Backlog, Analysis, Selected for Development, In-Progress, Showcase, Done
```

## How to change jira application?
Open ```release/env/host.txt``` and change the host url.

By default, we use ```https://arlive.atlassian.net```
