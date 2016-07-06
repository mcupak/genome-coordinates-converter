import hgvs.variantmapper
import hgvs.validator
import hgvs.dataproviders.uta
import hgvs.parser
import sys
import json
import re


#
# Given a genomic accession number, convert it to the appropriate reference
#
def getReference(ac):
    chr = re.sub('[a-zA-z]*_0+', "", ac).split(".")[0]
    chr_num = int(chr)
    if chr_num > 22:
        if chr_num == 23:
            chr = "X"
        elif chr_num == 24:
            chr = "Y"
    return chr


#
#
#
def main(args):
    if len(args) < 1:
        sys.exit(1)

    parser = hgvs.parser.Parser()
    connection = hgvs.dataproviders.uta.connect()
    validator = hgvs.validator.Validator(hdp=connection)
    variantmapper = hgvs.variantmapper.EasyVariantMapper(connection)

    conversions = []

    for arg in args:

        try:
            var = parser.parse_hgvs_variant(arg)
        except:
            throwResposne = [dict(error="%s is an invalid HGVS variant" % (arg), variant=arg)]
            print json.dumps(throwResposne)
            sys.exit(0)

        if var.type == "c":
            var = variantmapper.c_to_g(var)
        elif var.type == "n":
            var = variantmapper.n_to_g(var)

        try:
            validator.validate(var)
        except:
            throwResposne = [dict(error="%s is an invalid HGVS variant" % (arg), variant=arg)]
            print json.dumps(throwResposne)
            sys.exit(0)

        chr = getReference(var.ac)
        interval = var.posedit.pos
        conversions.append(dict(variant=arg, start=interval.start.base, end=interval.end.base, ref=chr))

    print json.dumps(conversions)
    exit(0)


if __name__ == "__main__":
    main(sys.argv[1:])
