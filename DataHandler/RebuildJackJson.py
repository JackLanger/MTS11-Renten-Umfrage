import json

QUESTIONTYPE_TRANSLATION = {
    "SINGLEANSWER": 1,
    "MULTIPLECHOICE": 2,
    "COMBO": 4,
}


def rebuild_jack_json(jack_json_path):
    with open(jack_json_path, "r") as f:
        data = json.load(f)
    data_json = {}
    header_json = {}

    for header in data[0]:
        print(header)
        if header["type"] in QUESTIONTYPE_TRANSLATION:
            header_json[header["id"]] = {
                "question_type": QUESTIONTYPE_TRANSLATION[header["type"]],
                "question_text": header["text"],
                "answer_options": header["answers"]
            }
        else:
            header_json[header["id"]] = {
                "question_type": 1,
                "question_text": header["text"],
                "answer_options": header["answers"]
            }


    for question in data[1]:
        print(question)
        data_json[question["userid"]] = question["answers"]


    print(json.dumps(data_json, indent=4, ensure_ascii=False))
    print(json.dumps(header_json, indent=4, ensure_ascii=False))
    return data_json, header_json

