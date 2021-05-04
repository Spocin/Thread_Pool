
# Thread pool
![](https://img.shields.io/badge/display%20type-GUI-brightgreen) ![](https://img.shields.io/badge/framework%20used-JavaFX-blueviolet)

This project was created as a part of a subject: **Uniwersalne Techniki Programowania**

This project was created to implement and learn multi-thread managment using ***Executors*** and ***Futures***.

## GUI
![](https://i.imgur.com/ok1y0bo.png)

## Description
**Each thread task is to pick random number and sum it with with previous ones utill limit is reached.**

Limit is computed with formula : `limit = Thread_number * 100`

Time beetwen drawings is randomized within fixed range up to `0,2` sec.

Value of drawn number is randomized within fixed range `[0,50]`.

Clicking on `Create New` button creates new thread, adds button coresponding to it to the bottom pane and waits to be clicked on (task started).

Each button at the bottom pane represents *thread*.

## Reqirements
- Java installed
- JavaFX on the machine

## Instalation
- Go to `Artifacts/`
- Open `Thread_Pool.bat` in text editor
- Replace `%JAVAFX%` with the path to the `JavaFx/lib` on your machine and save.
- Start `Thread_Pool.bat`

**Each thread can be:**
- `Started` - by left-clicking on th thread button while label says `Tx`
- `Suspended` - by left-clickin on the thread button while label says `Suspend Tx`
- `Canceled` - by holding down 'C' and left-clicking on the thread button
- `Completed` - when thread limit is reached button will disable and after 2 sec will be removed from the pane

Clicking on `STOP` button freezes all task and disables all buttons. After clicking `STOP` only possible action is to close programme.
