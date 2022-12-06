import json
import os
import sys
import RebuildJackJson

from ExcelWriter import ExcelWriter

BASE_DIR = os.path.dirname(os.path.abspath(__file__))


def run(data, header, download_path):
    excel_writer = ExcelWriter(download_path)
    excel_writer.create_cover(header)
    excel_writer.create_data(data, header)
    excel_writer.save()


if __name__ == '__main__':
    download_path_index = sys.argv[sys.argv[1:].index("-downloadpath") + 1]
    data_path_index = sys.argv[sys.argv[1:].index("-datapath") + 1]

    data, header = RebuildJackJson.rebuild_jack_json(data_path_index)
    run(data, header, download_path_index)
