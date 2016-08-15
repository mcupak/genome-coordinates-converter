from setuptools import setup

setup(name="hgvs converter",
      version=1.0,
      description="Wrapper aroundg PyPi package hgvs",
      url="https://github.com/mcupak/genome-coordinates-converter",
      author="patmagee",
      author_email="patrickmageee@gmail.com",
      license="apache2",
      install_requires=[
          "hgvs"
      ])