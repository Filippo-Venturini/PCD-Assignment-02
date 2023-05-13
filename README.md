# Asynchronous Source Tracker
This repo contains four subprojects that realize a tool for tracking the size of a Java project,
realizing a ranking of the sources in base of their lines of code and a distribution, in four different ways.

## Framework Executors
This approach uses the framework Executors, in particular **ForkJoinPool** and **RecursiveTask** for scanning in a recursive way the directories.

## Virtual Threads
In this part the same problem is solved by executing the Tasks via **Virtual Threads**, the architecture is similar to the previous one.

## Framework Vert.x
In this subproject the architecture is different, its envolve some **AbstractVerticle** working as Agents, communicating via the **Eventbus** for solving the problem.

## Framework RxJava
In this last approach are used some of the most common mechanism of reactive programming such as **Flowable**, **Observable**, **Subscribers** and so on.
