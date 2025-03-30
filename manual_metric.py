import json

def evaluate_predictions(data):
    """
    Displays the 'middle_expected' and 'predicted' values side-by-side
    for each item in the input data and allows manual evaluation.

    Args:
        data (list): A list of dictionaries, where each dictionary contains
                     'middle_expected' and 'predicted' keys.

    Returns:
        list: The updated list of dictionaries with added 'manual_evaluation'.
    """
    evaluated_data = []
    for i, item in enumerate(data):
        print(f"\n--- Item {i + 1}, FILENAME: {item.get('filename', 'N/A')} ---")
        print(f"Middle Expected: \n\n {item.get('middle_expected', 'N/A')}\n")
        print(f"Predicted:       \n\n {item.get('predicted', 'N/A')}\n")

        while True:
            evaluation = input("Enter manual evaluation (e.g., 1 for good, 0 for bad, skip to leave null): ").strip()
            if evaluation.lower() == 'skip':
                item['manual_evaluation'] = None
                break
            try:
                evaluation_num = int(evaluation)
                item['manual_evaluation'] = evaluation_num
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
    with open(filename, 'w') as f:
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
        with open(filename, 'r') as f:
            return json.load(f)
    except FileNotFoundError:
        print(f"Error: File '{filename}' not found.")
        return None
    except json.JSONDecodeError:
        print(f"Error: Could not decode JSON from file '{filename}'.")
        return None

if __name__ == "__main__":
    input_filename = "mode_completion_dataset2(7).json"

    input_data = load_from_json(input_filename)

    if input_data:
        evaluated_data = evaluate_predictions(input_data)
        save_to_json(evaluated_data)