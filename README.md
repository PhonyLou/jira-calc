## What's this?
This is a tool to generate Jira Cycle Time report

[![Build Status](https://travis-ci.org/zhangyuan/jira-calc.svg?branch=master)](https://travis-ci.org/zhangyuan/jira-calc)

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

    Then copy the token into ```.env/token.txt``` with the format like:
    ```
    jira-token: [Paste-Your-Token-Here]
    ```

### Config JQL

Update ```.env/jql.txt``` file to satisfy your team.

### Config Stages(Make sure use the correct stage, you can debug from network in jira to get the exactly value)

Update ```.env/stages.txt``` file to satisfy your team.

### Get report:
```
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
Open ./env/jql.txt and change the filter condition

### TianZhou
#### JQL (Juno):
```
jql: project = JUNO AND (assignee in (Liang.Cui, Jingjun.Zhang, Qilin.Lou, Jingtian.Mo, Hongxing.Chen, Yunpeng.Ding) OR assignee was in (Liang.Cui, Jingjun.Zhang, Qilin.Lou, Jingtian.Mo, Hongxing.Chen, Yunpeng.Ding)) AND status changed to done before now() after -30d  ORDER BY priority DESC
```

#### Stages (Juno):
```
card-stage: Backlog, Analysis, Selected for Development, In-Progress, Showcase, Done
```

#### JQL (Tianzhou):
```
jql: project = tianzhou AND (assignee in (Liang.Cui, Jingjun.Zhang, Qilin.Lou, Jingtian.Mo, Hongxing.Chen, Yunpeng.Ding) OR assignee was in (Liang.Cui, Jingjun.Zhang, Qilin.Lou, Jingtian.Mo, Hongxing.Chen, Yunpeng.Ding)) ORDER BY priority DESC
```

#### Stages (Tianzhou):
```
card-stage: READY FOR DEV, In Progress, Blocked, Showcase, Done
```

### ShenZhou
#### JQL:
```
jql: (assignee in (Jianing.Zheng, Minghao.Tan, Jie.Chen, Yingjuan.Wang, pei.wang2) OR assignee was in (Jianing.Zheng, Minghao.Tan, Jie.Chen, Yingjuan.Wang, pei.wang2)) AND status changed to Complete before now() after -30d  ORDER BY priority DESC
```

#### Stages:
```
card-stage: Backlog, Ready for Dev, In-Progress, Waiting (other dependancy), Complete
```

### YiLong
#### JQL:
```
jql: (assignee in (siyu.gou, sebastian.thiel, ke.liu,Hanxian.Lin,nan.li,junli.li,lin.fang) OR assignee was in (sebastian.thiel, ke.liu,Hanxian.Lin,nan.li,junli.li,lin.fang)) ORDER BY priority DESC
```

#### Stages:
```
card-stage: To Do, In Progress, TEST, Ready For Release, Done
```
