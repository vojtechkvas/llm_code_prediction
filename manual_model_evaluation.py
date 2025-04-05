import json
import argparse
from pygments import highlight
from pygments.lexers import JavaLexer
from pygments.formatters import TerminalFormatter


def evaluate_predictions(data):
    """
    Displays the 'middle' and 'middle_prediction' values side-by-side
    for each item in the input data and allows manual evaluation.

    Args:
        data (list): A list of dictionaries, where each dictionary contains
                     'middle' and 'middle_prediction' keys.

    Returns:
        list: The updated list of dictionaries with added 'manual_evaluation'.
    """
    evaluated_data = []
    for i, item in enumerate(data):
        print(f"\n--- Item {i + 1}, FILENAME: \033[92m{item.get('filename', 'N/A')}\033[0m ---")

        print(f"\033[91m---------------- Middle Expected ----------------\n\n\033[0m")

        print_colored_java(item.get("middle", "Nothing to show"))

        print(f"\033[91m---------------- Middle Predicted ----------------\n\n\033[0m")

        print_colored_java(item.get("middle_prediction", "Nothing to show"))

        while True:
            evaluation = input(
                "Enter manual evaluation (e.g., 0 for bad, 10 for best): "
            ).strip()
            if evaluation.lower() == "skip":
                item["manual_evaluation"] = None
                break
            try:
                evaluation_num = int(evaluation)
                item["manual_evaluation"] = evaluation_num
                break
            except ValueError:
                print("Invalid input. Please enter a number or 'skip'.")
        evaluated_data.append(item)

        print(f"\n\n\n\n")

    return evaluated_data


def save_to_json(data, filename="evaluated_predictions.json"):
    """
    Saves the given data to a JSON file.

    Args:
        data (list): The data to be saved.
        filename (str, optional): The name of the JSON file.
                                   Defaults to "evaluated_predictions.json".
    """
    with open(filename, "w") as f:
        json.dump(data, f, indent=4)
    print(f"\nResults saved to {filename}")


def load_from_json(filename):
    """
    Loads data from a JSON file.

    Args:
        filename (str): The name of the JSON file to load.

    Returns:
        list: The data loaded from the JSON file.
    """
    try:
        with open(filename, "r") as f:
            return json.load(f)
    except FileNotFoundError:
        print(f"Error: File '{filename}' not found.")
        return None
    except json.JSONDecodeError:
        print(f"Error: Could not decode JSON from file '{filename}'.")
        return None


def print_colored_java(java_code):
    """
    Print Java code with syntax highlighting to the terminal.

    Args:
        java_code (str): The Java code to be highlighted
    """
    colored_output = highlight(java_code, JavaLexer(), TerminalFormatter())
    print(colored_output)


if __name__ == "__main__":

    parser = argparse.ArgumentParser(
        description="Evaluate predictions from a JSON file."
    )
    parser.add_argument(
        "filename", type=str, help="Input JSON file containing predictions"
    )
    args = parser.parse_args()

    input_data = load_from_json(args.filename)

    if input_data:
        evaluated_data = evaluate_predictions(input_data)
        save_to_json(evaluated_data, "data_manually_evaluated.json")
