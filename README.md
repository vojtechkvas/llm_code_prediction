# LLM code prediction

This project focuses on using language models (LLM) to predict Java code for the backend of the Sincoolka Club information system. After the code prediction, we compare various metrics to evaluate the performance and accuracy of the predictions.

## Current Issue: Dependency Problem with codebleu Package

Currently, there is an issue with the codebleu package and its dependencies, causing errors in the system. To resolve this issue, follow the simple steps below to install the necessary dependencies directly from the GitHub repository

Easy Fix:

1. Install codebleu from GitHub:

```
pip install git+https://github.com/k4black/codebleu#egg=codebleu
```

2. Install the tree-sitter-java package:

```
pip install tree-sitter-java
```