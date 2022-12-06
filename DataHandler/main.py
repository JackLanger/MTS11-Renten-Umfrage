import json
import os
import sys
import RebuildJackJson

from ExcelWriter import ExcelWriter

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DOWNLOAD_DIR = "/bin/venv/media/python/Data.xlsx"
CACHE_DIR = "/bin/venv/media/python/cache/data.json"


def run(data, header, download_path):
    excel_writer = ExcelWriter(download_path)
    excel_writer.create_cover(header)
    excel_writer.create_data(data, header)
    excel_writer.save()


if __name__ == '__main__':
    download_path_index = sys.argv[sys.argv[1:].index("-downloadpath") + 1]
    data_path_index = sys.argv[sys.argv[1:].index("-datapath") + 1]

    data, header = RebuildJackJson.rebuild_jack_json(CACHE_DIR)
    run(data, header, DOWNLOAD_DIR)
