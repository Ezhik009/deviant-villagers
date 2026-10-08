import json,glob,os
S={os.path.basename(f)[:-5]:json.load(open(f)) for f in glob.glob('sheets/*.json')}
bad=[]
def walk(sheet,rid,v,path=""):
    if isinstance(v,dict):
        for k,x in v.items(): walk(sheet,rid,x,path+"."+k)
    elif isinstance(v,list):
        for i,x in enumerate(v): walk(sheet,rid,x,f"{path}[{i}]")
    elif v is None or v=="" : bad.append(f"UNFILLED  {sheet}:{rid}{path}")
    elif v is False and path.endswith("verified"): bad.append(f"UNVERIFIED {sheet}:{rid}")
for n,rows in S.items():
    for r in rows: walk(n,r.get("id","?"),r)
ids=lambda n:{r["id"] for r in S[n]}
for r in S["deviation_outcomes"]:
    for k,sh in (("detroitSoundRef","detroit_assets"),("detroitTextRef","detroit_assets")):
        if r[k] not in ids(sh): bad.append(f"BROKEN REF deviation_outcomes:{r['id']}.{k}={r[k]}")
for r in S["revolution_meter"]:
    if r["detroitSoundRef"] not in ids("detroit_assets"): bad.append(f"BROKEN REF revolution_meter:{r['id']}")
for c in S["choices"]:
    for o in c["options"]:
        for x in o["outcomeIds"]:
            if x not in ids("deviation_outcomes"): bad.append(f"BROKEN REF choices:{c['id']}->{x}")
print("\n".join(bad) if bad else "CLEAN"); print(f"{len(bad)} findings")
raise SystemExit(1 if bad else 0)
