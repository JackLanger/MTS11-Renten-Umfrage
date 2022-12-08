import csv
import json

CACHE_DIR = "/bin/venv/media/python/cache/data.json"
DOWNLOAD_DIR = "/bin/venv/media/python/Data.csv"


def csv_reader(json_path):
    """
    Read a csv file
    """

    with open(CACHE_DIR, "r") as f_obj:
        reader = json.loads(f_obj.read())
        with open(json_path, "w") as csv_file:
            writer = csv.writer(csv_file, delimiter=";")
            for line in reader[1]:
                writer.writerow(line["answers"].values())


if __name__ == '__main__':
    csv_reader(DOWNLOAD_DIR)
